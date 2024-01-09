package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public ClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
	}

	public void run() {
		try {
			System.out.println(netIn.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String command;
			try {
				command = reader.readLine();
				netOut.println(command);
				String response = netIn.readLine();
				if (response.equals("view")) {
					int length = Integer.parseInt(netIn.readLine());
					for (int i = 0; i < length; i++) {
						String line = netIn.readLine();
						System.out.println(line);
					}
				} else if (response.equals("closed")) {
					break;
				} else {
					System.out.println(response);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		try {
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
