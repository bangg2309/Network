package server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Service {
	private RandomAccessFile raf;
	public static final String URL = "D:\\Project\\Network\\Exams\\Exam2021-2022\\RAF\\DATA\\thisinh.dat";

	public Service() throws IOException {
		File file = new File(URL);
		if (!file.exists()) {
			file.createNewFile();
			raf = new RandomAccessFile(file, "rw");
			// ghi header
			raf.writeInt(0);
		} else {
			raf = new RandomAccessFile(file, "rw");
		}
	}

	private long locationCandidateById(int id) {
		id = id - 1;
		long pointer = id * (4 + 50 + 50 + 20 + 100000) + 4;

		return pointer;
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
		return str;
	}

	public boolean isValidAge(Candidate candidate) {
		return candidate.isValidAge();
	}

	public int register(Candidate candidate) throws IOException {
		int id;
		raf.seek(0);
		int size = raf.readInt();
		size++;
		raf.seek(0);
		raf.writeInt(size);
		id = size;

		String name = candidate.getName();
		String birth = candidate.getBirth();
		String address = candidate.getAddress();
		byte[] fileImg = new byte[100000];

		long pointer = raf.getFilePointer();	

		raf.seek(raf.length());
		raf.writeInt(id);
		writeFixedLength(raf, name, 25);
		writeFixedLength(raf, birth, 10);
		writeFixedLength(raf, address, 25);
		raf.write(fileImg);

		return id;
	}

	public void saveImage(int id, byte[] buff) throws IOException {
		long pointer = locationCandidateById(id);
		pointer += 4 + 50 + 50 + 20;
		raf.seek(pointer);
		raf.write(buff);
	}

	public Candidate findById(int id1) throws IOException {
		long pointer = locationCandidateById(id1);
		System.out.println(pointer);
		raf.seek(pointer);
		int id = raf.readInt();
		String name = readFixedLength(raf, 25);
		String birth = readFixedLength(raf, 10);
		String address = readFixedLength(raf, 25);
		byte[] fileImg = new byte[100000];
		raf.read(fileImg);
		return new Candidate(id, name, birth, address, fileImg);
	}

	public static void main(String[] args) throws IOException {
		Service service = new Service();
		System.out.println(service.findById(1).toString());
	}

}
