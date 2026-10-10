import importlib.util
from pathlib import Path
import subprocess
import sys
import tempfile
import time
import unittest
import json
import signal

SPEC = importlib.util.spec_from_file_location(
    'lighting_client_lifecycle', Path(__file__).resolve().parents[1] /
    'scripts/lighting_client_lifecycle.py')
lifecycle = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(lifecycle)


class InterruptedClient:
    def __init__(self, client):
        self.client = client
        self.pid = client.pid
        self.first = True

    def wait(self, **kwargs):
        if self.first:
            self.first = False
            raise KeyboardInterrupt()
        return self.client.wait(**kwargs)

    def poll(self):
        return self.client.poll()

    def send_signal(self, value):
        self.client.send_signal(value)


class LightingClientLifecycleTests(unittest.TestCase):
    def test_configuration_runner_termination_cleans_child_and_does_not_dispatch_next_row(self):
        with tempfile.TemporaryDirectory() as name:
            directory = Path(name)
            source = directory / 'child.py'
            source.write_text('''import signal,time,sys
from pathlib import Path
p=Path(sys.argv[1])
def stop(sig,frame):
    (p/'cleanup').write_text('saved')
    raise SystemExit(130)
signal.signal(signal.SIGINT,stop)
(p/'ready').touch()
while True:time.sleep(.01)
''')
            manifest = directory / 'manifest.json'
            result = directory / 'results.json'
            command = [sys.executable,str(source),str(directory),'--output',str(directory/'first')]
            next_command = [sys.executable,'-c',
                'from pathlib import Path; import sys; Path(sys.argv[1]).touch()',
                str(directory/'next-dispatched'),'--output',str(directory/'second')]
            manifest.write_text(json.dumps([
                {'name':'first','target':'fixture','command':command},
                {'name':'second','target':'fixture','command':next_command}]))
            runner = Path(__file__).resolve().parents[1]/'scripts/run-spectral-configurations.py'
            client = self.launch_runner(runner,manifest,result,directory)
            client.send_signal(signal.SIGTERM)
            self.assertEqual(130,client.wait(timeout=10))
            self.assertEqual('saved',(directory/'cleanup').read_text())
            self.assertFalse((directory/'next-dispatched').exists())
            rows=json.loads(result.read_text())
            self.assertEqual([('first','cancelled')],[(r['name'],r['status']) for r in rows])

    def launch_runner(self,runner,manifest,result,directory):
        client=subprocess.Popen([sys.executable,str(runner),str(manifest),
                                 '--results',str(result)],start_new_session=True,
                                stdout=subprocess.DEVNULL)
        self.addCleanup(self.cleanup,client)
        deadline=time.monotonic()+5
        while not (directory/'ready').exists():
            self.assertIsNone(client.poll())
            if time.monotonic()>=deadline:self.fail('Configuration child did not become ready')
            time.sleep(.01)
        return client

    def launch(self, source, directory):
        client = subprocess.Popen([sys.executable, '-c', source, str(directory)],
                                  start_new_session=True)
        self.addCleanup(self.cleanup, client)
        deadline = time.monotonic() + 5
        while not (directory / 'ready').exists():
            self.assertIsNone(client.poll())
            if time.monotonic() >= deadline:
                self.fail('Private test child did not become ready')
            time.sleep(.01)
        return client

    @staticmethod
    def cleanup(client):
        import os
        import signal
        try:
            os.killpg(client.pid, signal.SIGKILL)
        except ProcessLookupError:
            pass
        client.wait(timeout=5)

    def test_normal_completion(self):
        client = subprocess.Popen([sys.executable, '-c', 'raise SystemExit(7)'],
                                  start_new_session=True)
        self.addCleanup(self.cleanup, client)
        self.assertEqual(7, lifecycle.wait_client(client))

    def test_normal_harness_exit_stops_orphaned_worker(self):
        with tempfile.TemporaryDirectory() as name:
            directory = Path(name)
            client = self.launch("""
import subprocess,sys,time
from pathlib import Path
p=Path(sys.argv[1])
child=subprocess.Popen([sys.executable,'-c','import time; time.sleep(60)'])
(p/'descendant').write_text(str(child.pid));(p/'ready').touch()
time.sleep(.1)
raise SystemExit(7)
""", directory)
            self.assertEqual(7, lifecycle.wait_client(client))
            pid = int((directory/'descendant').read_text())
            stat = Path('/proc')/str(pid)/'stat'
            self.assertTrue(not stat.exists() or stat.read_text().rsplit(')',1)[1].split()[0]=='Z',
                            'Private worker survived its harness exiting normally')

    def test_interrupt_preserves_cleanup_and_stops_dispatch(self):
        with tempfile.TemporaryDirectory() as name:
            directory = Path(name)
            client = self.launch("""
import signal,sys,time
from pathlib import Path
p=Path(sys.argv[1])
def stop(sig,frame):
    (p/'cleanup').write_text('saved evidence')
    raise SystemExit(130)
signal.signal(signal.SIGINT,stop)
(p/'ready').touch()
while True:time.sleep(.1)
""", directory)
            with self.assertRaises(KeyboardInterrupt):
                lifecycle.wait_client(InterruptedClient(client), cleanup_timeout=2)
            self.assertEqual('saved evidence', (directory/'cleanup').read_text())
            self.assertEqual(130, client.poll())

    def test_responsive_cancelled_harness_cannot_leave_worker_running(self):
        with tempfile.TemporaryDirectory() as name:
            directory = Path(name)
            client = self.launch("""
import signal,sys,time,subprocess
from pathlib import Path
p=Path(sys.argv[1])
signal.signal(signal.SIGINT,lambda *args: sys.exit(130))
child=subprocess.Popen([sys.executable,'-c',
 'import signal,time; signal.signal(signal.SIGTERM,signal.SIG_IGN); time.sleep(60)'])
(p/'descendant').write_text(str(child.pid))
time.sleep(.1)
(p/'ready').touch()
while True:time.sleep(.1)
""", directory)
            with self.assertRaises(KeyboardInterrupt):
                lifecycle.wait_client(InterruptedClient(client), cleanup_timeout=2)
            pid = int((directory/'descendant').read_text())
            deadline = time.monotonic()+2
            while time.monotonic()<deadline:
                stat = Path('/proc')/str(pid)/'stat'
                if not stat.exists() or stat.read_text().rsplit(')',1)[1].split()[0]=='Z':
                    break
                time.sleep(.01)
            else:
                self.fail('Private worker survived its responsive cancelled harness')

    def test_unresponsive_harness_and_descendant_are_stopped(self):
        with tempfile.TemporaryDirectory() as name:
            directory = Path(name)
            client = self.launch("""
import signal,sys,time,subprocess
from pathlib import Path
p=Path(sys.argv[1]);signal.signal(signal.SIGINT,signal.SIG_IGN)
child=subprocess.Popen([sys.executable,'-c','import time; time.sleep(60)'])
(p/'descendant').write_text(str(child.pid));(p/'ready').touch()
while True:time.sleep(.1)
""", directory)
            with self.assertRaises(KeyboardInterrupt):
                lifecycle.wait_client(InterruptedClient(client), cleanup_timeout=.1)
            self.assertIsNotNone(client.poll())
            pid=int((directory/'descendant').read_text())
            deadline=time.monotonic()+2
            while time.monotonic()<deadline:
                stat=Path('/proc')/str(pid)/'stat'
                if not stat.exists() or stat.read_text().split()[2]=='Z':break
                time.sleep(.01)
            else:self.fail('Private descendant survived batch cancellation')
