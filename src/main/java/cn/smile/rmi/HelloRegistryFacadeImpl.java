package cn.smile.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRegistryFacadeImpl extends UnicastRemoteObject implements HelloRegistryFacade{
        public HelloRegistryFacadeImpl() throws RemoteException {
            super();
        }

        @Override
        public String helloWorld(String name) {
            return "[Registry] 你好! " + name;
        }
}
