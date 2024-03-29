package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private Service service;
	private DataInputStream dis;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
		this.service = new Service();
		this.dis = new DataInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		netOut.println("WELCOME!");

		while (true) {
			try {
				String request = netIn.readLine();
				StringTokenizer st = new StringTokenizer(request, "\t");
				String commandName = st.nextToken();
				int countTokens = st.countTokens();

				switch (commandName) {
				case "REGISTER":
					if (countTokens != 3) {
						netOut.println("tham so nhap vao khong hop le");
					} else {
						String name = st.nextToken();
						String birth = st.nextToken();
						String address = st.nextToken();
						Candidate candidate = new Candidate(name, birth, address);
						if (service.isValidAge(candidate)) {
							int id = service.register(candidate);
							netOut.println("OK");
							netOut.println(id);
						} else {
							netOut.println("Tuoi khong phu hop tham gia cuoc thi");
						}

					}
					break;
				case "FOTO":
					String fileUrl = st.nextToken();
					int id = Integer.parseInt(netIn.readLine());
					long length = Long.parseLong(netIn.readLine());
					if (countTokens != 2) {
						netOut.println("Command khong hop le");
					}
					
					else if (!fileUrl.endsWith(".jpg")) {
						netOut.println("extends file must .jpg");
					} else if (length > 100000) {
						netOut.println("Size image too large");
					} else {
						netOut.println("OK");
						byte[] buff = new byte[1000000];
						dis.read(buff);
						service.saveImage(id, buff);
					}
					break;
				case "VIEW":
					int id1 = Integer.parseInt(st.nextToken());
					Candidate candidate = service.findById(id1);
					netOut.println(candidate.toString());
					break;

				default:
					netOut.println("Command khong hop le");
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
