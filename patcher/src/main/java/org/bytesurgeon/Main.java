package org.bytesurgeon;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println(
                "Usage: java -jar bytesurgeon-patcher.jar " +
                "-input in.jar -output out.jar");
            System.exit(1);
        }

        Path input = null;
        Path output = null;

        for (int i = 0; i < args.length; i += 2) {
            String key = args[i];
            if (i + 1 >= args.length) {
                throw new IllegalArgumentException("Missing values for argument" + key);
            }
            String value = args[i + 1];

            switch (key) {
                case "-input" -> input = Path.of(value);
                case "-output" -> output = Path.of(value); 
            }
        }

        if (input == null || !Files.exists(input)) { throw new IllegalArgumentException("Input files doesnot exists: " + input); }
        if (output == null) { throw new IllegalArgumentException("Output file not specified"); }
        else if (!Files.exists(output)) {
            Files.createFile(output);
            System.out.println("Output file created: " + output);
        }
        JarPatcher.patch(input, output); 
    }
}
