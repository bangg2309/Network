package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IProductManagement extends Remote {

	boolean checkUsername(String username) throws RemoteException, ClassNotFoundException, SQLException;

	String login(String username, String password) throws RemoteException, ClassNotFoundException, SQLException;

	String addProduct(String sessionId, Product product) throws RemoteException, ClassNotFoundException, SQLException;

	int removeProduct(String sessionId, List<Integer> ids) throws RemoteException, ClassNotFoundException, SQLException;

	String editProduct(String sessionId, Product product) throws RemoteException, ClassNotFoundException, SQLException;

	String findProductByName(String sessionId, String name) throws RemoteException, ClassNotFoundException, SQLException;

}
