package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import server.Server;

public class FileClientProcess {

	private Socket socket;
	private String command;
	private String type;
	private DataInputStream dis;
	private DataOutputStream dos;

	public FileClientProcess(Socket socket, String command, String type) throws IOException {
		this.socket = socket;
		this.command = command;
		this.type = type;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	public void run() throws IOException {

		switch (type) {
		case "UPLOAD":
			upload();
			break;
		case "DOWNLOAD":
			download();
			break;

		default:
			break;
		}
	}

	private void download() {
		String dest;
		try {
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			String sourceFile = st.nextToken();
			String destFile = st.nextToken();

			dos.writeUTF(sourceFile);
			dos.flush();

			Long fileSize = dis.readLong();

			FileOutputStream fos = new FileOutputStream(destFile);
			int offset;
			long byteRead = 0;
			byte[] buff = new byte[1024];
			while (byteRead < fileSize) {
				offset = dis.read(buff);
				fos.write(buff, 0, offset);
				byteRead += offset;
			}
			System.out.println(dis.readUTF());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void upload() {
		try {
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			String sourceFile = st.nextToken().trim();
			String destFile = st.nextToken().trim();

			dos.writeUTF(destFile);
			dos.flush();

			File file = new File(sourceFile);
			dos.writeLong(file.length());
			dos.flush();

			FileInputStream fis = new FileInputStream(file);

			byte[] buff = new byte[1024];
			int data;
			while ((data = fis.read(buff)) != -1) {
				dos.write(buff, 0, data);
				dos.flush();
			}
			System.out.println(dis.readUTF());
			fis.close();
		} catch (IOException e) {
			System.out.println("Upload that bai");
		}
	}

}
