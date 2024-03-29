package split;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SplitAndJoin {

	private static String makeFileName(String fileName, int order) {
		String str = "";
		if (order < 10) {
			str = ".00" + order;
		} else if (order < 100) {
			str = ".0" + order;
		} else {
			str = "" + order;
		}
		return fileName + str;
	}

	public static void splitFile(String sFile, int size) throws IOException {
		File file = new File(sFile);
		int order = 0;
		String fileName = file.getAbsolutePath();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sFile));
		boolean rs = false;
		String childFile;
		do {
			order++;
			childFile = makeFileName(fileName, order);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(childFile));
			rs = tranferTo(bos, bis, size);
			bos.close();
		} while (rs);
		bis.close();
	}

	public static void joinFile(String sFolder, String dFile) throws IOException {
		File folder = new File(sFolder);
		File[] files = folder.listFiles();
		int sizeFile = files.length;
		FileOutputStream fos = new FileOutputStream(dFile);
		for (int i = 0; i < sizeFile; i++) {
			File file = files[i];
			FileInputStream fis = new FileInputStream(file);
			copyFile(fos, fis);
			fis.close();
		}
		fos.close();
	}

	private static void copyFile(FileOutputStream fos, FileInputStream fis) throws IOException {
		byte[] buff = new byte[1024];
		int data;
		while ((data = fis.read(buff)) != -1) {
			fos.write(buff, 0, data);
		}
	}

	private static boolean tranferTo(BufferedOutputStream bos, BufferedInputStream bis, int size) throws IOException {
		byte[] buff = new byte[1024];
		int remainder = size;
		int readed;
		int data;
		while (remainder > 0) {
			readed = remainder < buff.length ? remainder : buff.length;
			data = bis.read(buff, 0, readed);
			if (data == -1) {
				return false;
			}
			bos.write(buff, 0, data);
			remainder -= data;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
//		splitFile("D:\\temp\\copy\\tempp.pdf", 1024000);
		joinFile("D:\\temp\\copy", "D:\\temp\\copy\\tempp.pdf");
	}
}
