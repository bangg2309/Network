package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	public static String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
	public static String URL = "jdbc:ucanaccess://D:\\Project\\Network\\Exam2018-2019\\ProductManagement.accdb";
	private Connection connection = null;

	private void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		this.connection = DriverManager.getConnection(URL);
	}

	public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
		openConnection();
		String sql = "SELECT * FROM user WHERE username = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, username);
		ResultSet rs = stmt.executeQuery();
		boolean result = rs.next();
		rs.close();
		stmt.close();
		connection.close();
		return result;

	}

	public boolean login(String username, String password) throws SQLException, ClassNotFoundException {
		openConnection();
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, username);
		stmt.setObject(2, password);
		ResultSet rs = stmt.executeQuery();
		boolean result = rs.next();
		rs.close();
		stmt.close();
		connection.close();
		return result;

	}

	public void addProduct(Product product) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "INSERT INTO sanpham(idsp,name,count,price) VALUES(?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, product.getId());
		stmt.setObject(2, product.getName());
		stmt.setObject(3, product.getCount());
		stmt.setObject(4, product.getPrice());
		stmt.executeUpdate();

		stmt.close();
		connection.close();
	}

	public int removeProduct(Integer id) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "DELETE FROM sanpham WHERE idsp = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, id);
		int result = stmt.executeUpdate();

		stmt.close();
		connection.close();
		return result;
	}

	public void updateProduct(Product product) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "UPDATE sanpham SET name = ?, count = ?, price = ? WHERE idsp = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, product.getName());
		stmt.setObject(2, product.getCount());
		stmt.setObject(3, product.getPrice());
		stmt.setObject(4, product.getId());
		stmt.executeUpdate();

		stmt.close();
		connection.close();
	}

	public List<Product> findProductByName(String name) throws ClassNotFoundException, SQLException {
		openConnection();
		name = "%" + name + "%";
		String sql = "SELECT * FROM sanpham WHERE name LIKE ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, name);
		ResultSet rs = stmt.executeQuery();
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt("idsp");
			String _name = rs.getString("name");
			int count = rs.getInt("count");
			double price = rs.getDouble("price");
			Product product = new Product(id, _name, count, price);
			products.add(product);
		}

		rs.close();
		stmt.close();
		connection.close();
		return products;
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DAO dao = new DAO();
		System.out.println(dao.findProductByName("lap"));
	}
}
