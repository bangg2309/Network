package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import server.IProductManagement;
import server.Product;

public class ClientApp {

	public static void main(String[] args) throws NotBoundException, IOException, ClassNotFoundException, SQLException {

		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
		IProductManagement management = (IProductManagement) registry.lookup("MANAGEMENT");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String sessionId = null;
		String lastUsername = null;
		while (true) {
			String command = reader.readLine();
			if (command.equalsIgnoreCase("EXIT")) {
				break;
			}
			StringTokenizer st = new StringTokenizer(command, "\t");
			String commandName = st.nextToken();
			int countTokens = st.countTokens();

			switch (commandName) {
			case "USER":
				lastUsername = st.nextToken();
				if (management.checkUsername(lastUsername)) {
					System.out.println("OK");
				} else {
					System.out.println("FALSE");
				}
				break;
			case "PASS":
				if (lastUsername == null) {
					System.out.println("Vui long nhap username truoc");
				}
				String password = st.nextToken();
				sessionId = management.login(lastUsername, password);
				if (sessionId != null) {
					System.out.println("Dang nhap thanh cong");
				}
				else {
					System.out.println("Mat khau khong hop le");
				}
				break;
			case "ADD":
				int id = Integer.parseInt(st.nextToken());
				String name = st.nextToken();
				int count = Integer.parseInt(st.nextToken());
				double price = Double.parseDouble(st.nextToken());
				Product product = new Product(id, name, count, price);
				String response = management.addProduct(sessionId, product);
				System.out.println(response);
				break;
			case "REMOVE":
				List<Integer> ids = new ArrayList<>();
				for (int i = 0; i < countTokens; i++) {
					ids.add(Integer.parseInt(st.nextToken()));
				}
				System.out.println(management.removeProduct(sessionId, ids));
				break;
			case "UPDATE":
				Product p = new Product(Integer.parseInt(st.nextToken()), st.nextToken(),
						Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
				System.out.println(management.editProduct(sessionId, p));
				break;
			case "VIEW":
				String search = st.nextToken();
				System.out.println(management.findProductByName(sessionId, search));
				break;
			default:
				System.out.println("Command khong hop le");
				break;
			}
		}

	}

}
