package net.kohi.tcpnodelaymod;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class NetworkManagerInnerVisitor extends ClassVisitor {

    public NetworkManagerInnerVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM4, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("initChannel")) {
            return new MethodVisitor(Opcodes.ASM4, mv) {
                private String option;

                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                    if (opcode == Opcodes.GETSTATIC &&
                            owner.equals("io/netty/channel/ChannelOption") &&
                            desc.equals("Lio/netty/channel/ChannelOption;")) {
                        option = name;
                    }
                    mv.visitFieldInsn(opcode, owner, name, desc);
                }

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                    if (opcode == Opcodes.INVOKEINTERFACE &&
                            owner.equals("io/netty/channel/ChannelConfig") &&
                            name.equals("setOption") &&
                            desc.equals("(Lio/netty/channel/ChannelOption;Ljava/lang/Object;)Z")) {
                        if (option.equals("TCP_NODELAY")) {
                            LogManager.getLogger("TcpNoDelayMod").info("Setting TCP_NODELAY to true");
                            mv.visitInsn(Opcodes.POP);
                            mv.visitInsn(Opcodes.ICONST_1);
                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                            mv.visitMethodInsn(opcode, owner, name, desc);
                            return;
                        }
                    }
                    mv.visitMethodInsn(opcode, owner, name, desc);
                }
            };
        }
        return mv;
    }
}
