package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int MAIN_PORT = 2000;
	public static int FILE_PORT = 3000;

	private void startServer() throws IOException {
		ServerSocket mainSocket = new ServerSocket(MAIN_PORT);
		ServerSocket fileSocket = new ServerSocket(FILE_PORT);

		while (true) {
			MainServerProcess mainProcess = new MainServerProcess(fileSocket, mainSocket.accept());
			mainProcess.start();
		}

	}

	public static void main(String[] args) throws IOException {
		new Server().startServer();
	}

}
