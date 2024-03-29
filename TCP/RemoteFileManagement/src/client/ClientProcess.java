package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientProcess {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public ClientProcess(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
	}

	public void run() throws IOException {
		System.out.println(netIn.readLine());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String command = reader.readLine();
			StringTokenizer st = new StringTokenizer(command, "|");
			String commandName = st.nextToken();
			String response;
			int size;
			switch (commandName) {
			case "SET_FOLDER":
				netOut.println(command);
				System.out.println(netIn.readLine());
				break;
			case "VIEW":
				netOut.println(command);
				response = netIn.readLine();
				if (response.equals("folder")) {
					size = Integer.parseInt(netIn.readLine());
					System.out.println(netIn.readLine());
					for (int i = 0; i < size; i++) {
						System.out.println(netIn.readLine());
					}
				} else if (response.equals("file")) {
					size = 4;
					for (int i = 0; i < size; i++) {
						System.out.println(netIn.readLine());
					}
				} else {
					System.out.println(response);
				}
				break;
			case "COPY":
				netOut.println(command);
				System.out.println(netIn.readLine());
				break;
			case "MOVE":
				netOut.println(command);
				System.out.println(netIn.readLine());
				break;
			case "RENAME":
				netOut.println(command);
				System.out.println(netIn.readLine());
				break;

			default:
				netOut.println("Command khong hop le");
				break;
			}
		}
	}
}
