package cn.smile.classloader;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    @Test
    public void getClassLoaderTest(){

        /**
         * BootStrap ClassLoader：主要加载%JRE_HOME%\lib下的rt.jar、resources.jar、charsets.jar和class等。可以通System.getProperty("sun.boot.class.path")
         * */

//        System.out.println(System.getProperty("sun.boot.class.path"));

        /**
         * Extention ClassLoader：主要加载目录%JRE_HOME%\lib\ext目录下的jar包和class文件。也可以通过System.out.println(System.getProperty("java.ext.dirs"))查看加载类文件的路径。
         * */

        System.out.println(System.getProperty("java.ext.dirs"));

        /**
         * AppClassLoader：主要加载当前应用下的classpath路径下的类。之前我们在环境变量中配置的classpath就是指定AppClassLoader的类加载路径。
         * */
        System.out.println(System.getProperty("java.class.path"));

        System.out.println(System.getProperty("java.security.manager"));


        System.out.println(Dog.class.getClassLoader().toString());

        System.out.println(Dog.class.getClassLoader().getParent().toString());

        /**
         * 无父类
         * */
//        System.out.println(Dog.class.getClassLoader().getParent().getParent().toString());
    }

    @Test
    public void myClassLoaderTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //自定义类加载器的加载路径
        MyClassLoader myClassLoader=new MyClassLoader("D:\\CodeSpace\\JvmSurfing\\target\\classes");
        //包名+类名
        Class c=myClassLoader.loadClass("cn.smile.classloader.Dog");
        if(c!=null){
            Object obj=c.newInstance();
            Method method=c.getMethod("say", null);
            method.invoke(obj, null);
            System.out.println(c.getClassLoader().toString());
        }
    }

    @Test
    public void loadStringTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //自定义类加载器的加载路径
        MyClassLoader myClassLoader=new MyClassLoader("D:\\CodeSpace\\JvmSurfing\\target\\classes");
        //包名+类名
        Class c = myClassLoader.loadClass("java.lang.String");

        String a = new String();

    }
}
