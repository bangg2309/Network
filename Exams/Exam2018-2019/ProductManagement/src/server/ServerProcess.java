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
	private boolean isLogin;
	private Service service;

	public ServerProcess(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
		this.isLogin = false;
		this.service = new Service();
	}

	@Override
	public void run() {
		netOut.println("WELCOME TO MANAGE PRODUCT SYSTEM");
		String lastUsername = "";

		while (true) {
			while (!isLogin) {
				try {
					String command = netIn.readLine();
					if (command.equals("EXIT")) {
						netOut.println("closed");
						socket.close();
						return;
					}
					StringTokenizer st = new StringTokenizer(command, "\t");
					String commandName = st.nextToken();
					switch (commandName) {
					case "USER":
						lastUsername = st.nextToken();
						try {
							if (service.checkUsername(lastUsername)) {
								netOut.println("OK");
							} else {
								netOut.println("FALSE");
							}
						} catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
						break;
					case "PASS":
						String password = st.nextToken();
						try {
							if (service.login(lastUsername, password)) {
								netOut.println("OK");
								isLogin = true;
							} else {
								netOut.println("FALSE");
							}
						} catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
						break;

					default:
						netOut.println("Command khong hop le");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			while (isLogin) {
				try {
					String command = netIn.readLine();
					StringTokenizer st = new StringTokenizer(command, "\t");
					String commandName = st.nextToken();
					int countTokens = st.countTokens();
					switch (commandName) {
					case "ADD":
						if (countTokens != 4) {
							netOut.println("command khong hop le");
						} else {
							String id = st.nextToken();
							String name = st.nextToken();
							String count = st.nextToken();
							String price = st.nextToken();
							Product product = new Product(Integer.parseInt(id), name, Integer.parseInt(count),
									Double.parseDouble(price));
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
							netOut.println("command khong hop le");
						} else {
							List<Integer> ids = new ArrayList<>();
							for (int i = 0; i < countTokens; i++) {
								ids.add(i);
							}
							try {
								int rs = service.removeProduct(ids);
								netOut.println(rs);
							} catch (ClassNotFoundException | SQLException e) {
								netOut.println("ERROR");
							}
						}
						break;

					case "EDIT":
						if (countTokens != 4) {
							netOut.println("command khong hop le");
						} else {
							String id = st.nextToken();
							String name = st.nextToken();
							String count = st.nextToken();
							String price = st.nextToken();
							Product product = new Product(Integer.parseInt(id), name, Integer.parseInt(count),
									Double.parseDouble(price));
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
							netOut.println("command khong hop le");
						} else {
							String name = st.nextToken();
							try {
								String str = service.findProductByName(name);
								StringTokenizer dataTokenizer = new StringTokenizer(str, "\n");
								netOut.println("view");
								int length = dataTokenizer.countTokens();
								netOut.println(length);
								for (int i = 0; i < length; i++) {
									String line = dataTokenizer.nextToken();
									netOut.println(line);
								}
							} catch (ClassNotFoundException | SQLException e) {
								netOut.println("ERROR");
							}

						}
						break;
					case "QUIT":
						netOut.println("dang xuat thanh cong");
						isLogin = false;
						break;
					default:
						netOut.println("command khong hop le");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
