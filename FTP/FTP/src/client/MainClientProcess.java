package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class MainClientProcess {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public MainClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
	}

	public void run() throws IOException {
		System.out.println(netIn.readLine());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String command = reader.readLine();
			netOut.println(command);
			if (command.equalsIgnoreCase("QUIT") || command == null) {
				break;
			}
			StringTokenizer st = new StringTokenizer(command, " ");
			String commandName = st.nextToken();

			switch (commandName) {
			case "SEND":
				openFileSocket(command, "UPLOAD");
				break;
			case "DOWNLOAD":
				openFileSocket(command, "DOWNLOAD");
				break;
			default:
				String response = netIn.readLine();
				System.out.println(response);
				break;
			}
		}
	}

	private void openFileSocket(String command, String type) throws IOException {
		String response = netIn.readLine();
		if (response.equals("accept")) {
			Socket fileSocket = new Socket(Client.HOST, Client.FILE_PORT);
			FileClientProcess fileProcess = new FileClientProcess(fileSocket, command, type);
			fileProcess.run();
		} else {
			System.out.println("Server has refused your open upload socket command");
		}

	}

}
