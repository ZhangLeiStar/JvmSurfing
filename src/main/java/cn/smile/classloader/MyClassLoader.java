package cn.smile.classloader;

import java.io.*;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.concurrent.ConcurrentHashMap;

public class MyClassLoader extends ClassLoader {
    private String classpath;
    private final ProtectionDomain defaultDomain =
            new ProtectionDomain(new CodeSource(null, (Certificate[]) null),
                    null, this, null);

    public MyClassLoader(String classpath) {

        this.classpath = classpath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

//    protected Class<?> loadClass(String name, boolean resolve)
//            throws ClassNotFoundException
//    {
//        synchronized (getClassLoadingLock(name)) {
//            // First, check if the class has already been loaded
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                long t0 = System.nanoTime();
//
//                if (c == null) {
//                    // If still not found, then invoke findClass in order
//                    // to find the class.
//                    long t1 = System.nanoTime();
//                    c = findClass(name);
//
//                    // this is the defining class loader; record the stats
//                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
//                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
//                    sun.misc.PerfCounter.getFindClasses().increment();
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte [] classDate=getData(name);

            if(classDate==null){

            }else{
                //defineClass方法将字节码转化为类
                return defineClass(name,classDate,0,classDate.length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    protected final Class<?> defineClassMy(String name, byte[] b, int off, int len)
            throws ClassFormatError
    {
        return defineClassMy(name, b, off, len, null);
    }

    protected final Class<?> defineClassMy(String name, byte[] b, int off, int len,
                                         ProtectionDomain protectionDomain)
            throws ClassFormatError
    {
        protectionDomain = preDefineClass(name, protectionDomain);
        String source = defineClassSourceLocation(protectionDomain);
        Class<?> c = defineClass1(name, b, off, len, protectionDomain, source);
        postDefineClass(c, protectionDomain);
        return c;
    }

    private ProtectionDomain preDefineClass(String name,
                                            ProtectionDomain pd)
    {
            if (pd == null) {
                pd = defaultDomain;
            }

            if (name != null) checkCerts(name, pd.getCodeSource());

            return pd;
    }

    private void checkCerts(String name, CodeSource cs) {
        int i = name.lastIndexOf('.');
        String pname = (i == -1) ? "" : name.substring(0, i);

        Certificate[] certs = null;
        if (cs != null) {
            certs = cs.getCertificates();
        }
    }

    private String defineClassSourceLocation(ProtectionDomain pd)
    {
        CodeSource cs = pd.getCodeSource();
        String source = null;
        if (cs != null && cs.getLocation() != null) {
            source = cs.getLocation().toString();
        }
        return source;
    }

    private native Class<?> defineClass1(String name, byte[] b, int off, int len,
                                         ProtectionDomain pd, String source);

    private void postDefineClass(Class<?> c, ProtectionDomain pd)
    {
        if (pd.getCodeSource() != null) {
            Certificate certs[] = pd.getCodeSource().getCertificates();
            if (certs != null)
                setSigners(c, certs);
        }
    }


    //返回类的字节码
    private byte[] getData(String className) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path=classpath + File.separatorChar +
                className.replace('.',File.separatorChar)+".class";
        try {
            in=new FileInputStream(path);
            out=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            in.close();
            out.close();
        }
        return null;
    }
}
