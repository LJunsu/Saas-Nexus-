package kr.or.nexus.codeShare.javac;

public class FileClassLoader extends ClassLoader {
    public Class findClass(byte[] classByte, String name) throws ClassNotFoundException {
        return defineClass(name, classByte, 0, classByte.length);
    }
}
