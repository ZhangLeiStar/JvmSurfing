package cn.smile.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Registry(注册表)是放置所有服务器对象的命名空间。
 * 每次服务端创建一个对象时，它都会使用bind()或rebind()方法注册该对象。
 * 这些是使用称为绑定名称的唯一名称注册的。
 *
 * 要调用远程对象，客户端需要该对象的引用,如(HelloRegistryFacade)。
 * 即通过服务端绑定的名称(HelloRegistry)从注册表中获取对象(lookup()方法)。
 * */
public class RegistryService {
        public static void main(String[] args) {
            try {
                // 本地主机上的远程对象注册表Registry的实例,默认端口1099
                Registry registry = LocateRegistry.createRegistry(1099);
                // 创建一个远程对象
                HelloRegistryFacade hello = new HelloRegistryFacadeImpl();
                // 把远程对象注册到RMI注册服务器上，并命名为HelloRegistry
                registry.rebind("HelloRegistry", hello);
                System.out.println("======= 启动RMI服务成功! =======");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
}
