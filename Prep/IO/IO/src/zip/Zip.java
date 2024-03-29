package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Zip {
	public static boolean pack(String sFolder, String dFile) throws IOException {
		File folder = new File(sFolder);
		if (!folder.exists()) {
			return false;
		}
		File[] files = folder.listFiles();
		RandomAccessFile raf = new RandomAccessFile(dFile, "rw");
		boolean isLasted = false;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (i == files.length - 1) {
				isLasted = true;
			}
			writeFile(file, raf, isLasted);
		}
		raf.close();
		return true;
	}

	public static boolean unpack(String sFile, String dFolder) throws IOException {
		File file = new File(sFile);
		File foder = new File(dFolder);
		if (!file.exists() || !foder.exists()) {
			return false;
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		long nextEntry = 0;
		long fileSize;
		String fileName;
		boolean rs;
		do {
			nextEntry = raf.readLong();
			fileSize = raf.readLong();
			fileName = raf.readUTF();
			File childFile = new File(dFolder + File.separator + fileName);

			FileOutputStream fos = new FileOutputStream(childFile);
			transferTo(fos, raf, fileSize);
			fos.close();
			raf.seek(nextEntry);

		} while (nextEntry != 0);
		raf.close();

		return true;
	}

	private static boolean transferTo(FileOutputStream fos, RandomAccessFile raf, long fileSize) throws IOException {
		byte[] buff = new byte[1024];
		long remainder = fileSize;
		int readed;
		int data;
		while (remainder > 0) {
			readed = (int) ((remainder < buff.length) ? remainder : buff.length);
			data = raf.read(buff, 0, readed);
			if (data == -1) {
				return false;
			}
			fos.write(buff, 0, data);
			remainder -= readed;
		}
		return true;
	}

	private static void writeFile(File file, RandomAccessFile raf, boolean isLasted) throws IOException {
		long nextEntryLocation = raf.getFilePointer();
		raf.writeLong(0);
		raf.writeLong(file.length());
		raf.writeUTF(file.getName());
		FileInputStream fis = new FileInputStream(file);
		byte[] buff = new byte[1024];
		int data;
		while ((data = fis.read(buff)) != -1) {
			raf.write(buff, 0, data);
		}
		fis.close();
		if (!isLasted) {
			raf.seek(nextEntryLocation);
			raf.writeLong(raf.length());
			raf.seek(raf.length());
		}
	}

	public static void main(String[] args) throws IOException {
//		String sFolder = "D:\\image";
//		String dFile = "D:\\temp\\pack";
//		System.out.println(pack(sFolder, dFile));

		String sFile = "D:\\temp\\pack";
		String dFolder = "D:\\temp\\unpack";
		System.out.println(unpack(sFile, dFolder));
	}
}
