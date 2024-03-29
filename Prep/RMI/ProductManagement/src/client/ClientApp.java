package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import server.IProductManagement;
import server.Product;

public class ClientApp {
	public static void main(String[] args) throws IOException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6969);
		IProductManagement management = (IProductManagement) registry.lookup("MANAGEMENT");
		System.out.println(management.greeting());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;

		while (true) {
			line = reader.readLine();
			if (line.equals("QUIT")) {
				break;
			}
			StringTokenizer st = new StringTokenizer(line, "\t");
			String commandName = st.nextToken();
			int countToken = st.countTokens();
			int idsp, count;
			String name;
			double price;
			switch (commandName) {
			case "ADD":
				if (countToken != 4) {
					System.out.println("Command ADD khong hop le");
				} else {
					idsp = Integer.parseInt(st.nextToken());
					name = st.nextToken();
					count = Integer.parseInt(st.nextToken());
					price = Double.parseDouble(st.nextToken());
					Product product = new Product(idsp, name, count, price);

					try {
						management.addProduct(product);
						System.out.println("OK");
					} catch (RemoteException e) {
						System.out.println("ERROR");
					}

				}
				break;
			case "REMOVE":
				if (countToken < 1) {
					System.out.println("Command REMOVE khong hop le");
				} else {
					List<Integer> ids = new ArrayList<>();
					while (st.hasMoreTokens()) {
						ids.add(Integer.parseInt(st.nextToken()));
					}

					int removeSucess = management.removeProduct(ids);
					System.out.println(removeSucess);

				}
				break;
			case "EDIT":
				if (countToken != 4) {
					System.out.println("Command EDIT khong hop le");
				} else {
					idsp = Integer.parseInt(st.nextToken());
					name = st.nextToken();
					count = Integer.parseInt(st.nextToken());
					price = Double.parseDouble(st.nextToken());
					Product product = new Product(idsp, name, count, price);

					management.updateProduct(product);
					System.out.println("OK");

				}
				break;
			case "VIEW":
				if (countToken != 1) {
					System.out.println("Command VIEW khong hop le");
				} else {
					name = st.nextToken();

					List<Product> products = management.findByName(name);
					for (Product product : products) {
						System.out.println(product.toString());
					}
					System.out.println("THE END");

				}
				break;

			default:
				System.out.println("Command khong hop le");
				break;
			}
		}
	}
}
