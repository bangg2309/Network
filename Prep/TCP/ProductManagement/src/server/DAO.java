package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	private Connection connection;
	public static final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
	public static final String URL = "jdbc:ucanaccess://D:\\Project\\Network\\Prep\\TCP\\ProductManagement.accdb";

	private void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		this.connection = DriverManager.getConnection(URL);
	}

	public void addProduct(Product product) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "INSERT INTO sanpham (idsp,name,count,price) VALUES(?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, product.getIdsp());
		stmt.setObject(2, product.getName());
		stmt.setObject(3, product.getCount());
		stmt.setObject(4, product.getPrice());
		stmt.executeUpdate();

		stmt.close();
		connection.close();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DAO dao = new DAO();
		Product product = new Product(1, "LAPTOP", 23, 23000);
		dao.addProduct(product);
	}

	public void removeProduct(int id) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "DELETE FROM sanpham WHERE idsp = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, id);
		stmt.executeUpdate();

		stmt.close();
		connection.close();
	}

	public void updateProduct(Product product) throws ClassNotFoundException, SQLException {
		openConnection();
		String sql = "UPDATE sanpham SET name = ?, count = ?, price = ? WHERE idsp = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, product.getName());
		stmt.setObject(2, product.getCount());
		stmt.setObject(3, product.getPrice());
		stmt.setObject(4, product.getIdsp());
		stmt.executeUpdate();

		stmt.close();
		connection.close();
	}

	public List<Product> findByName(String name) throws ClassNotFoundException, SQLException {
		openConnection();
		name = "%" + name + "%";
		String sql = "SELECT * FROM sanpham WHERE name LIKE ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setObject(1, name);
		List<Product> products = new ArrayList<>();
		ResultSet rs = stmt.executeQuery();
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

}
