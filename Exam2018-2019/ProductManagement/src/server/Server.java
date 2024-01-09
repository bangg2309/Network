package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int PORT = 6969;

	private void startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		while (true) {
			ServerProcess process = new ServerProcess(serverSocket.accept());
			process.start();
		}
	}
	public static void main(String[] args) throws IOException {
		new Server().startServer();
	}
}
