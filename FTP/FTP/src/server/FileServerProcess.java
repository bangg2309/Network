package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class FileServerProcess extends Thread {
	private Socket socket;
	private String type;
	private DataInputStream dis;
	private DataOutputStream dos;

	public FileServerProcess(Socket socket, String type) throws IOException {
		this.socket = socket;
		this.type = type;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {

		switch (type) {
		case "SEND":
			sendFile();
			break;
		case "GET":
			getFile();
			break;
		default:
			break;
		}
	}

	private void getFile() {
		String sourceFile;
		try {
			sourceFile = dis.readUTF();
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

			fis.close();
			dos.writeUTF("download success");
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendFile() {
		System.out.println("PORT: " + Server.FILE_PORT + " has opened");
		String dest;
		try {
			dest = dis.readUTF();
			Long length = dis.readLong();
			FileOutputStream fos = new FileOutputStream(dest);

			int offset;
			long byteRead = 0;
			byte[] buff = new byte[1024];
			while (byteRead < length) {
				offset = dis.read(buff);
				fos.write(buff, 0, offset);
				byteRead += offset;
			}
			dos.writeUTF("upload success");
			dos.flush();
			fos.close();
			System.out.println("PORT: " + Server.FILE_PORT + " has closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
