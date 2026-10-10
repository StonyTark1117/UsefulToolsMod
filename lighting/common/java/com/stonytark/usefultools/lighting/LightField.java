package com.stonytark.usefultools.lighting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Render-only light field. Immutable snapshots are safe for chunk mesh worker threads. */
public final class LightField {
    public static final class Source {
        public final double x, y, z;
        public final int level, radius;
        public Source(double x, double y, double z, int level, int radius) {
            if (level < 1 || level > 15 || radius < 1 || radius > 30) throw new IllegalArgumentException("Invalid light source");
            this.x = x; this.y = y; this.z = z; this.level = level; this.radius = radius;
        }
        public double at(double px, double py, double pz) {
            double dx = px - x, dy = py - y, dz = pz - z;
            return Math.max(0, level * (1 - Math.sqrt(dx * dx + dy * dy + dz * dz) / radius));
        }
        public boolean same(Source other) {
            return other != null && x == other.x && y == other.y && z == other.z && level == other.level && radius == other.radius;
        }
    }
    private Map<String, Source> sources = new HashMap<String, Source>();
    private final Map<Long, Map<String, Source>> bucketSources = new HashMap<Long, Map<String, Source>>();
    private volatile Map<Long, List<Source>> snapshot = Collections.emptyMap();
    private final Set<Long> dirty = new HashSet<Long>();
    public static long section(int x, int y, int z) {
        return ((long)x & 0x3FFFFFL) << 42 | ((long)z & 0x3FFFFFL) << 20 | (y & 0xFFFFFL);
    }
    public static int sectionX(long key) { return (int)(key >> 42); }
    public static int sectionY(long key) { return (int)(key << 44 >> 44); }
    public static int sectionZ(long key) { return (int)(key << 22 >> 42); }
    private static void sections(Source source, Set<Long> output) {
        int r = source.radius + 1; // include boundary neighbors used by ambient occlusion sampling
        for (int x = (int)Math.floor(source.x - r) >> 4; x <= (int)Math.floor(source.x + r) >> 4; x++)
            for (int y = (int)Math.floor(source.y - r) >> 4; y <= (int)Math.floor(source.y + r) >> 4; y++)
                for (int z = (int)Math.floor(source.z - r) >> 4; z <= (int)Math.floor(source.z + r) >> 4; z++) output.add(section(x, y, z));
    }
    /** Replace one complete client-frame inventory; removed sources dirty their former sections. */
    public void update(Map<String, Source> next) {
        Set<Long> changedSections = new HashSet<Long>();
        for (Map.Entry<String, Source> entry : sources.entrySet()) {
            if (!entry.getValue().same(next.get(entry.getKey()))) {
                Set<Long> oldSections = new HashSet<Long>(); sections(entry.getValue(), oldSections);
                for (long key : oldSections) {
                    Map<String, Source> bucket = bucketSources.get(key);
                    if (bucket != null) { bucket.remove(entry.getKey()); if (bucket.isEmpty()) bucketSources.remove(key); }
                }
                changedSections.addAll(oldSections);
            }
        }
        for (Map.Entry<String, Source> entry : next.entrySet()) {
            if (!entry.getValue().same(sources.get(entry.getKey()))) {
                Set<Long> newSections = new HashSet<Long>(); sections(entry.getValue(), newSections);
                for (long key : newSections) {
                    Map<String, Source> bucket = bucketSources.get(key);
                    if (bucket == null) { bucket = new HashMap<String, Source>(); bucketSources.put(key, bucket); }
                    bucket.put(entry.getKey(), entry.getValue());
                }
                changedSections.addAll(newSections);
            }
        }
        if (changedSections.isEmpty()) return;
        Map<Long, List<Source>> immutable = new HashMap<Long, List<Source>>(snapshot);
        for (long key : changedSections) {
            Map<String, Source> bucket = bucketSources.get(key);
            if (bucket == null) immutable.remove(key);
            else immutable.put(key, Collections.unmodifiableList(new ArrayList<Source>(bucket.values())));
        }
        dirty.addAll(changedSections);
        sources = new HashMap<String, Source>(next);
        snapshot = Collections.unmodifiableMap(immutable);
    }
    public Set<Long> drainDirty() { Set<Long> result = new HashSet<Long>(dirty); dirty.clear(); return result; }
    public void clear() { update(Collections.<String, Source>emptyMap()); }
    public int size() { return sources.size(); }
    public double light(double x, double y, double z) {
        List<Source> bucket = snapshot.get(section((int)Math.floor(x) >> 4, (int)Math.floor(y) >> 4, (int)Math.floor(z) >> 4));
        double result = 0;
        if (bucket != null) for (Source source : bucket) result = Math.max(result, source.at(x, y, z));
        return result;
    }
    /** Retain every sky-light bit and only raise the renderer's block-light component. */
    public int packed(int original, double x, double y, double z) {
        int visual = Math.min(240, (int)(light(x, y, z) * 16));
        return (original & ~0xFF) | Math.max(original & 0xFF, visual);
    }
    /** Sodium 0.5/0.6 cache layout: low nibble is block light; all other fields survive. */
    public int sodium(int original, int x, int y, int z) {
        int visual = (int)Math.ceil(light(x + .5, y + .5, z + .5) - 1e-9);
        return (original & ~15) | Math.max(original & 15, Math.min(15, visual));
    }
}
