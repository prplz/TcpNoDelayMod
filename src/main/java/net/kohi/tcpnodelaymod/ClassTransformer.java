package net.kohi.tcpnodelaymod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        
        if (transformedName.equals("net.minecraft.network.NetworkManager$2")) {
            try {
                ClassReader classReader = new ClassReader(bytes);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor classVisitor = new NetworkManager2Visitor(classWriter);
                classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
                return classWriter.toByteArray();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return bytes;
    }
}
