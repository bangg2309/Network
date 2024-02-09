package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	public static int MAIN_PORT = 2000;
	public static int FILE_PORT = 3000;
	public static String HOST = "127.0.0.1";

	private void startClient() throws UnknownHostException, IOException {
		socket = new Socket(HOST, MAIN_PORT);
		MainClientProcess mainProcess = new MainClientProcess(socket);
		mainProcess.run();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client().startClient();
	}
}
