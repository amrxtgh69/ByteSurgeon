package org.bytesurgeon;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;


final class JarPatcher {
    static void patch(Path input, Path output) throws Exception {
       try (JarFile jar = new JarFile(input.toFile());
       JarOutputStream jos = new JarOutputStream(Files.newOutputStream(output))) {

        jar.stream().forEach(entry -> {
            try (InputStream is = jar.getInputStream(entry)) {
                JarEntry outEntry = new JarEntry(entry.getName());
                jos.putNextEntry(outEntry);

                if (entry.getName().endsWith(".class")) {
                   byte[] patched = ClassPatcher.patch(is);
                   jos.write(patched);
                } else {
                    is.transferTo(jos);
                }

                jos.closeEntry();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
       } 
    }
}
