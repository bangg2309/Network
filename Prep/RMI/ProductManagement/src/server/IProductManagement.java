package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IProductManagement extends Remote {

	String greeting() throws RemoteException;

	void addProduct(Product product) throws RemoteException;

	int removeProduct(List<Integer> ids) throws RemoteException;

	void updateProduct(Product product) throws RemoteException;

	List<Product> findByName(String name) throws RemoteException;
}
