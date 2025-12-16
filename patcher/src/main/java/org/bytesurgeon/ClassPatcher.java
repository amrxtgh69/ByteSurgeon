package org.bytesurgeon;

import java.io.InputStream;

import org.objectweb.asm.*;

final class ClassPatcher {
    static byte[] patch(InputStream in) throws Exception {
        ClassReader cr = new ClassReader(in);

        ClassWriter cw = new ClassWriter(
            ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS
        );
        ClassVisitor cv = new ClassVisitor(ASM9, cw) {

            private String className;
            @Override
            public void visit(
                int version,
                int access,
                String name,
                String signature,
                String superName,
                String[] interfaces) {
                    this.className = name.replace("/", ".");
                    super.visit(version, access, name, signature, interfaces);
                }
            }
        }

        return cw.toByteArray();
    }
}