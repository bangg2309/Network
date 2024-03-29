package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerApp {
	public static final int PORT = 2000;

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(PORT);

		IProductManagement management = new ProductManagementImpl();
		registry.bind("MANAGEMENT", management);
	}

}
