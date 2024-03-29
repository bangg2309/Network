package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private Service service;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
		this.service = new Service();
	}

	@Override
	public void run() {
		netOut.println("WELCOME TO MANAGE PRODUCT SYSTEM");

		while (true) {
			try {
				String command = netIn.readLine();
				if (command.equals("QUIT"))
					break;
				StringTokenizer st = new StringTokenizer(command, "\t");
				String commandName = st.nextToken();
				int countTokens = st.countTokens();

				switch (commandName) {
				case "ADD":
					if (countTokens != 4) {
						netOut.println("command ADD khong hop le");
					} else {
						int idsp = Integer.parseInt(st.nextToken());
						String name = st.nextToken();
						int count = Integer.parseInt(st.nextToken());
						double price = Double.parseDouble(st.nextToken());
						Product product = new Product(idsp, name, count, price);
						try {
							service.addProduct(product);
							netOut.println("OK");
						} catch (ClassNotFoundException | SQLException e) {
							netOut.println("ERROR");
						}
					}
					break;
				case "REMOVE":
					if (countTokens < 1) {
						netOut.println("command REMOVE khong hop le");
					} else {
						List<Integer> ids = new ArrayList<>();
						while (st.hasMoreTokens()) {
							ids.add(Integer.parseInt(st.nextToken()));
						}
						try {
							int count = service.removeProduct(ids);
							netOut.println(count);
						} catch (ClassNotFoundException | SQLException e) {
							netOut.println("ERROR");
						}
					}
					break;
				case "EDIT":
					if (countTokens != 4) {
						netOut.println("command EDIT khong hop le");
					} else {
						int idsp = Integer.parseInt(st.nextToken());
						String name = st.nextToken();
						int count = Integer.parseInt(st.nextToken());
						double price = Double.parseDouble(st.nextToken());
						Product product = new Product(idsp, name, count, price);
						try {
							service.updateProduct(product);
							netOut.println("OK");
						} catch (ClassNotFoundException | SQLException e) {
							netOut.println("CAN NOT UPDATE");
						}
					}
					break;
				case "VIEW":
					if (countTokens != 1) {
						netOut.println("Command VIEW khong hop le");
					} else {
						try {
							String name = st.nextToken();
							List<Product> products = service.findByName(name);

							for (Product product : products) {
								netOut.println(product.toString());
								System.out.println(product.toString());
							}
							netOut.println("THE END");
						} catch (ClassNotFoundException | SQLException e) {
							netOut.println("ERROR");
						}
					}
					break;

				default:
					netOut.println("command khong hop le");
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
