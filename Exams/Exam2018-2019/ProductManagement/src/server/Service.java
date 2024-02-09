package server;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Service {
	private DAO dao;

	public Service() {
		this.dao = new DAO();
	}

	public boolean checkUsername(String username) throws ClassNotFoundException, SQLException {
		return dao.checkUsername(username);
	}

	public boolean login(String username, String password) throws ClassNotFoundException, SQLException {
		return dao.login(username, password);
	}

	public void addProduct(Product product) throws ClassNotFoundException, SQLException {
		dao.addProduct(product);
	}

	public int removeProduct(List<Integer> ids) throws ClassNotFoundException, SQLException {
		int count = 0;
		for (Integer id : ids) {
			dao.removeProduct(id);
			count++;
		}
		return count;

	}

	public void updateProduct(Product product) throws ClassNotFoundException, SQLException {
		dao.updateProduct(product);
	}

	public String findProductByName(String name) throws ClassNotFoundException, SQLException {
		List<Product> products = dao.findProductByName(name);
		String str = "";
		for (Product product : products) {
			str += product.toString() + "\n";
		}
		str += "THE END";
		return str;
	}

}
