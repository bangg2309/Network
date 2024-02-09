package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public ServerProcess(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		netOut.println("Welcom to File Management");
		String folderDefault = "";
		while (true) {
			try {
				String command = netIn.readLine();
				if (command.equals("QUIT"))
					break;
				StringTokenizer st = new StringTokenizer(command, "|");
				String commandName = st.nextToken();
				String path, file, sourceFile, destFile;
				boolean rs;
				switch (commandName) {
				case "SET_FOLDER":
					path = st.nextToken();
					if (isFolder(path)) {
						folderDefault = path;

						netOut.println("OK");
					} else {
						netOut.println("ERROR");
					}
					System.out.println(folderDefault);
					break;
				case "VIEW":
					String fileOrPath = st.nextToken();
					if (isFolder(fileOrPath)) {
						netOut.println("folder");
						System.out.println("folder");
						getPathAndFile(fileOrPath);
					} else if (isFile(folderDefault, fileOrPath)) {
						netOut.println("file");
						System.out.println("file");
						file = folderDefault + File.separator + fileOrPath;
						File f = new File(file);
						netOut.println(f.getAbsolutePath());
						netOut.println(f.length());
						netOut.println(f.canRead());
						netOut.println(f.canWrite());
					} else {
						netOut.println("ERROR");
					}
					break;
				case "COPY":
					sourceFile = st.nextToken();
					destFile = st.nextToken();
					rs = copyFile(sourceFile, destFile, false);
					if (rs) {
						netOut.println("OK");
					} else {
						netOut.println("ERROR");
					}
					break;
				case "MOVE":
					sourceFile = st.nextToken();
					destFile = st.nextToken();
					rs = copyFile(sourceFile, destFile, true);
					if (rs) {
						netOut.println("OK");
					} else {
						netOut.println("ERROR");
					}
					break;
				case "RENAME":
					sourceFile = st.nextToken();
					destFile = st.nextToken();

					rs = copyFile(sourceFile, destFile, true);
					if (rs) {
						netOut.println("OK");
					} else {
						netOut.println("ERROR");
					}
					break;

				default:
					netOut.println("Command khong hop le");
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean copyFile(String sourceFile, String destFile, boolean isMove) throws IOException {
		File file = new File(sourceFile);
		if (!file.exists() && !file.canRead()) {
			return false;
		}

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

		byte[] buff = new byte[1024];
		int data;
		while ((data = bis.read(buff)) != -1) {
			bos.write(buff, 0, data);
		}
		bis.close();
		bos.close();
		if (isMove) {
			file.delete();
		}
		return true;
	}

	private void getPathAndFile(String path) {
		File folder = new File(path);

		File[] files = folder.listFiles();
		netOut.println(files.length);
		netOut.println(path);
		for (int i = 0; i < files.length; i++) {
			netOut.println(files[i].getAbsolutePath());
		}
	}

	private boolean isFolder(String path) {
		File file = new File(path);
		return file.isDirectory();

	}

	private boolean isFile(String path, String fileName) {
		String file = path + File.separator + fileName;
		return new File(file).isFile();
	}

}
