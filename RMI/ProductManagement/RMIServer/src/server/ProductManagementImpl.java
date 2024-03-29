package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProductManagementImpl extends UnicastRemoteObject implements IProductManagement {

	private Service service;
	private Set<String> session;

	protected ProductManagementImpl() throws RemoteException {
		this.service = new Service();
		this.session = new HashSet<>();
	}

	@Override
	public boolean checkUsername(String username) throws RemoteException, ClassNotFoundException, SQLException {
		return service.checkUsername(username);
	}

	@Override
	public String login(String username, String password) throws RemoteException, ClassNotFoundException, SQLException {
		if (service.login(username, password)) {
			String sessionId = UUID.randomUUID().toString();
			session.add(sessionId);
			return sessionId;
		}
		return null;
	}

	@Override
	public String addProduct(String sessionId, Product product) throws RemoteException, ClassNotFoundException, SQLException {
		String message;
		if (session.contains(sessionId)) {
				service.addProduct(product);
				message = "OK";

		} else {
			message = "Vui long dang nhap";
		}

		return message;

	}

	@Override
	public int removeProduct(String sessionId, List<Integer> ids)
			throws RemoteException, ClassNotFoundException, SQLException {
		if (session.contains(sessionId)) {
			return service.removeProduct(ids);
		}
		return 0;
	}

	@Override
	public String editProduct(String sessionId, Product product) throws RemoteException, ClassNotFoundException, SQLException {
		String message;
		if (session.contains(sessionId)) {
				service.updateProduct(product);
				message = "OK";

		} else {
			message = "Vui long dang nhap";
		}
		return message;
	}

	@Override
	public String findProductByName(String sessionId, String name)
			throws RemoteException, ClassNotFoundException, SQLException {
		String message = "";
		if (session.contains(sessionId)) {
			List<Product> products = service.findProductByName(name);
			for (Product product : products) {
				message += product.toString() + "\n";
			}
			message += "THE END";
		}

		else {
			message = "Vui long dang nhap";
		}
		return message;
	}

}
