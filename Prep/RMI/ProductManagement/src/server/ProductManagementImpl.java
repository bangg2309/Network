package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ProductManagementImpl extends UnicastRemoteObject implements IProductManagement {

	private Service service;

	protected ProductManagementImpl() throws RemoteException {
		super();
		this.service = new Service();
	}

	@Override
	public void addProduct(Product product) throws RemoteException {
		try {
			service.addProduct(product);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RemoteException(e.getMessage());
		}

	}

	@Override
	public int removeProduct(List<Integer> ids) throws RemoteException {
		try {
			return service.removeProduct(ids);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RemoteException(e.getMessage());
		}

	}

	@Override
	public void updateProduct(Product product) throws RemoteException {
		try {
			service.updateProduct(product);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RemoteException(e.getMessage());
		}

	}

	@Override
	public List<Product> findByName(String name) throws RemoteException {
		try {
			return service.findByName(name);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public String greeting() throws RemoteException {
		return "WELCOME TO PRODUCT MANAGEMENT";
	}

}
