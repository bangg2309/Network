package raf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManagement {
	private RandomAccessFile raf;
	private String filePath;

	public FileManagement(String filePath) throws IOException {
		File dao = new File(filePath);
		if (!dao.exists()) {
			raf = new RandomAccessFile(dao, "rw");
			raf.writeInt(0);
		} else {
			raf = new RandomAccessFile(dao, "rw");
		}
	}

	public long getLocationById(int id) {
		id = id - 1;
		long pointer = id * (4 + 50 + 50 + 20 + 100000) + 4;

		return pointer;
	}

	public int countSizeImage(RandomAccessFile dis, int len) throws IOException {
		byte[] bytes = new byte[len];
		raf.read(bytes);
		int size = 0;
		for (int i = 0; i < bytes.length; i++)
			if (bytes[i] != 0)
				size++;
		return size;
	}

	public void writeFixedLength(DataOutput dos, String str, int length) throws IOException {
		char c;
		for (int i = 0; i < length; i++) {
			c = (i < str.length()) ? str.charAt(i) : 0;
			dos.writeChar(c);
		}
	}

	public String readFixedLength(DataInput dis, int length) throws IOException {
		String str = "";
		char c;
		for (int i = 0; i < length; i++) {
			c = dis.readChar();

			if (c != 0) {
				str += c;
			}
		}
		System.out.println(str);
		return str;
	}

	public int register(String name, String birh, String address) throws IOException {
		Candidate candidate = new Candidate(name, birh, address);
		if (!candidate.isValidBirth(2024)) {
			return -1;
		}
		raf.seek(0);
		int size = raf.readInt();
		size++;
		raf.seek(0);
		raf.writeInt(size);

		int id = size;
		raf.seek(getLocationById(id));
		raf.writeInt(id);
		writeFixedLength(raf, name, 25);
		writeFixedLength(raf, birh, 10);
		writeFixedLength(raf, address, 25);
		byte[] buff = new byte[100000];
		raf.write(buff);
		raf.seek(raf.length());
		return id;
	}

	public Candidate findById(int id) throws IOException {
		raf.seek(getLocationById(id));
		int idCandidate = raf.readInt();
		String name = readFixedLength(raf, 25);
		String birth = readFixedLength(raf, 10);
		String address = readFixedLength(raf, 25);
		int sizeImage = countSizeImage(raf, 100000);
		Candidate candidate = new Candidate(name, birth, address);
		candidate.setId(idCandidate);
		candidate.setSizeImage(sizeImage);
		return candidate;
	}

	public boolean uploadFile(int id, String filePath) throws IOException {
		File file = new File(filePath);
		long len = file.length();
		boolean isImage = filePath.endsWith(".jpg");
		if (len > 100000 || !isImage) {
			return false;
		}
		FileInputStream fis = new FileInputStream(file);
		long pointer = getLocationById(id) + 4 + 50 + 50 + 20;
		System.out.println(pointer);
		raf.seek(pointer);
		copyFile(fis, raf);
		System.out.println(raf.getFilePointer());
		raf.seek(raf.length());
		fis.close();
		return true;
	}

	private void copyFile(FileInputStream fis, DataOutput dos) throws IOException {
		byte[] buff = new byte[1024];
		int data;
		while ((data = fis.read(buff)) != -1) {
			dos.write(buff, 0, data);
		}
	}

	public static void main(String[] args) throws IOException {
		String filePath = "D:\\Project\\Network\\Prep\\IO\\IO\\src\\raf\\DATA\\thisinh.dat";
		FileManagement management = new FileManagement(filePath);
		Candidate ca1 = new Candidate("Nguyen Van A", "22/10/2020", "Ha Noi");
		System.out.println(management.register("Nguyen Van A", "22/10/2020", "Ha Noi"));

		System.out.println(management.findById(1));
		System.out.println(management.uploadFile(1, "D:\\image\\1.jpg"));
		System.out.println(management.findById(1));
		System.out.println(management.register("Nguyen Van B", "22/10/2020", "Ha Noi"));
		System.out.println(management.findById(2));
	}

}
