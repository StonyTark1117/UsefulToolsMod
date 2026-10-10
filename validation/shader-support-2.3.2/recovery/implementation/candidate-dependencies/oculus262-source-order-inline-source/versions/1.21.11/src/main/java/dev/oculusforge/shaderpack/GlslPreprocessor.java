package dev.oculusforge.shaderpack;

import java.util.Map;
import org.anarres.cpp.DefaultPreprocessorListener;
import org.anarres.cpp.Feature;
import org.anarres.cpp.LexerException;
import org.anarres.cpp.Preprocessor;
import org.anarres.cpp.Source;
import org.anarres.cpp.StringLexerSource;
import org.anarres.cpp.Token;

/** Preprocesses expanded GLSL without changing the shader pack on disk. */
public final class GlslPreprocessor {
    private static final String VERSION_MARKER = "#warning OCULUS_JCPP_GLSL_VERSION";
    private static final String EXTENSION_MARKER = "#warning OCULUS_JCPP_GLSL_EXTENSION";

    private GlslPreprocessor() {
    }

    public static String preprocess(String input, Map<String, String> environment) throws ShaderPackException {
        if (input.contains(VERSION_MARKER) || input.contains(EXTENSION_MARKER)) {
            throw new ShaderPackException(
                ShaderPackException.Code.PREPROCESSING_FAILED,
                "Shader source contains a reserved Oculus preprocessor marker"
            );
        }

        String source = input.replace("#version", VERSION_MARKER)
            .replace("#extension", EXTENSION_MARKER)
            .replace("\u0000", "");
        CollectingListener listener = new CollectingListener();

        @SuppressWarnings("resource")
        Preprocessor preprocessor = new Preprocessor();
        try {
            for (Map.Entry<String, String> definition : environment.entrySet()) {
                preprocessor.addMacro(definition.getKey(), definition.getValue());
            }
            preprocessor.setListener(listener);
            preprocessor.addInput(new StringLexerSource(source.endsWith("\n") ? source : source + "\n", true));
            preprocessor.addFeature(Feature.KEEPCOMMENTS);

            StringBuilder result = new StringBuilder(source.length());
            while (true) {
                Token token = preprocessor.token();
                if (token == null || token.getType() == Token.EOF) {
                    break;
                }
                result.append(token.getText());
            }
            return listener.collectedLines() + result.append('\n').toString();
        } catch (Exception exception) {
            throw new ShaderPackException(
                ShaderPackException.Code.PREPROCESSING_FAILED,
                "GLSL source preprocessing failed",
                null,
                exception
            );
        }
    }

    private static final class CollectingListener extends DefaultPreprocessorListener {
        private final StringBuilder lines = new StringBuilder();

        @Override
        public void handleWarning(Source source, int line, int column, String message) throws LexerException {
            if (message.startsWith(VERSION_MARKER)) {
                lines.append(message.replace(VERSION_MARKER, "#version ")).append('\n');
            } else if (message.startsWith(EXTENSION_MARKER)) {
                lines.append(message.replace(EXTENSION_MARKER, "#extension ")).append('\n');
            } else {
                super.handleWarning(source, line, column, message);
            }
        }

        private String collectedLines() {
            return lines.toString();
        }
    }
}
