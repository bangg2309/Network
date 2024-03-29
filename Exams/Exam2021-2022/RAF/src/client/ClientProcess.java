package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientProcess {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private DataOutputStream dos;
	private boolean isRegistered;
	private boolean isUploaded;
	private int id;

	public ClientProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
		this.dos = new DataOutputStream(socket.getOutputStream());
		this.isRegistered = false;
		this.isUploaded = false;
	}

	public void run() throws IOException {
		System.out.println(netIn.readLine());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String command;
		while (true) {
			command = reader.readLine();
			StringTokenizer st = new StringTokenizer(command, "\t");
			String commandName = st.nextToken();

			switch (commandName) {
			case "REGISTER":

				if (!isRegistered) {
					netOut.println(command);

					String response = netIn.readLine();
					if (response.equals("OK")) {
						isRegistered = true;
						this.id = Integer.parseInt(netIn.readLine());
						System.out.println(id);
					}
				} else {
					System.out.println("Ban da dang ky roi");
				}
				break;
			case "FOTO":
				if (isRegistered) {
					if (!isUploaded) {
						netOut.println(command);
						String url = st.nextToken();
						File file = new File(url);
						netOut.println(id);
						netOut.println(file.length());

						String respone = netIn.readLine();
						if (respone.equals("OK")) {
							BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
							byte[] buff = new byte[100000];
							bis.read(buff);
							dos.write(buff);
							dos.flush();
							bis.close();

							isUploaded = true;

						} else {
							System.out.println(respone);
						}
					} else {
						System.out.println("Khong duoc up load lai");
					}
				} else {
					System.out.println("Vui long dang ky truoc khi upload");
				}

				break;
			case "VIEW":

				netOut.println(command);

				System.out.println(netIn.readLine());

				break;

			default:
				break;
			}
		}
	}

}
