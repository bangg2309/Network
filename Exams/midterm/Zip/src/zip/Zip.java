package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Zip {
	public static boolean pack(String sFolder, String dFile) throws IOException {
		File folder = new File(sFolder);
		if (!folder.isDirectory()) {
			return false;
		}
		File[] files = folder.listFiles();
		RandomAccessFile raf = new RandomAccessFile(dFile, "rw");
		boolean isLastFile = false;
		for (int i = 0; i < files.length; i++) {
			File fileItem = files[i];
			isLastFile = (i == files.length - 1);
			writeFile(fileItem, raf, isLastFile);
		}
		raf.close();
		return true;
	}

	public static boolean unpack(String sFile, String dFolder) throws IOException {
		File file = new File(sFile);
		if (!file.isFile()) {
			return false;
		}
		File folder = new File(dFolder);
		if (!folder.isDirectory()) {
			folder.mkdir();
		}
		String path = folder.getAbsolutePath();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		readFile(raf, path);
		raf.close();
		return true;
	}

	private static void readFile(RandomAccessFile raf, String path) throws IOException {
		long nextEntry;
		do {
			nextEntry = raf.readLong();
			long fileSize = raf.readLong();
			String fileName = path + File.separator + raf.readUTF();
			File file = new File(fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			for (int i = 0; i < fileSize; i++) {
				fos.write(raf.read());
			}
		} while (nextEntry != 0);
	}

	private static void writeFile(File file, RandomAccessFile raf, boolean isLastFile) throws IOException {
		long nextEntry = 0;
		long fileSize = file.length();
		String fileName = file.getName();

		long locationNextEntry = raf.getFilePointer();
		raf.writeLong(0);
		raf.writeLong(fileSize);
		raf.writeUTF(fileName);

		FileInputStream fis = new FileInputStream(file);
		byte[] buff = new byte[1024];
		int data = 0;
		while ((data = fis.read(buff)) != -1) {
			raf.write(buff, 0, data);
		}
		fis.close();
		if (!isLastFile) {
			nextEntry = raf.getFilePointer();
			raf.seek(locationNextEntry);
			raf.writeLong(nextEntry);
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
