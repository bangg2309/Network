package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainServerProcess extends Thread {
	private Socket socket;
	private ServerSocket fileServer;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private List<User> users = new ArrayList<>();
	private boolean isLogin;

	public MainServerProcess(ServerSocket fileServer, Socket socket) throws IOException {
		this.socket = socket;
		this.fileServer = fileServer;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
		User admin = new User("admin", "adminpass");
		users.add(admin);
		this.isLogin = false;
	}

	@Override
	public void run() {
		netOut.println("Welcome!");
		String lastUsername = null;
		while (!isLogin) {
			try {
				String command = netIn.readLine();
				if (command == null || "QUIT".equalsIgnoreCase(command))
					break;
				StringTokenizer st = new StringTokenizer(command, " ");
				String commandName = st.nextToken();
				switch (commandName) {
				case "TEN":
					lastUsername = st.nextToken();
					if (checkUsername(lastUsername)) {
						netOut.println("OK");
					} else {
						netOut.println("FALSE");
					}
					break;
				case "MATKHAU":
					String password = st.nextToken();
					if (lastUsername == null) {
						netOut.println("Vui long nhap username truoc");
					} else if (login(lastUsername, password)) {
						isLogin = true;
						netOut.println("OK");

					} else {
						netOut.println("FALSE");
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

		while (isLogin) {
			try {
				String command = netIn.readLine();
				StringTokenizer st = new StringTokenizer(command);
				String commandName = st.nextToken();

				switch (commandName) {
				case "SEND":
					openFileSocket("SEND");
					break;
				case "GET":
					openFileSocket("GET");
					break;

				default:
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openFileSocket(String type) throws IOException {
		netOut.println("accept");

		Socket fileSocket = fileServer.accept();
		FileServerProcess fileServerProcess = new FileServerProcess(fileSocket, type);
		fileServerProcess.start();
	}

	private boolean checkUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	private boolean login(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
