import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SplitAndJoinFile {

	private static String makeDestFileName(String sourceFile, int order) {
		String ext;
		if (order < 10) {
			ext = ".00" + order;
		} else if (order < 100) {
			ext = ".0" + order;
		} else {
			ext = "." + order;
		}
		return sourceFile + ext;
	}

	public static void splitFile(String sourceFile, int size) throws IOException {
		FileInputStream fis = new FileInputStream(sourceFile);
		String destFile;
		FileOutputStream fos;
		boolean rs = false;
		int order = 0;

		do {
			order++;
			destFile = makeDestFileName(sourceFile, order);
			System.out.println(destFile);
			fos = new FileOutputStream(destFile);
			rs = tranferTo(fis, fos, size);
			fos.close();
		} while (rs);
		fis.close();

	}

	public static void joinFile(String sourceFolder, String destFile) throws IOException {
		File folder = new File(sourceFolder);
		File[] files = folder.listFiles();
		FileOutputStream fos = new FileOutputStream(destFile);
		FileInputStream fis;
		for (int i = 0; i < files.length; i++) {
			fis = new FileInputStream(files[i].getAbsoluteFile());
			copyFile(fis, fos);
			fis.close();
		}
		fos.close();
	}

	private static void copyFile(FileInputStream fis, FileOutputStream fos) throws IOException {
		byte[] buff = new byte[1024];
		int data;
		while ((data = fis.read(buff)) != -1) {
			fos.write(buff, 0, data);
		}
	}

	private static boolean tranferTo(FileInputStream fis, FileOutputStream fos, int size) throws IOException {
		byte[] buff = new byte[1024];
		int remain = size;
		int data;
		int readed;
		while (remain > 0) {
			data = remain < buff.length ? remain : buff.length;
			readed = fis.read(buff, 0, data);
			if (readed == -1) {
				return false;
			}
			fos.write(buff, 0, readed);
			;
			remain -= readed;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
//		splitFile("D:\\temp\\copy\\tempp.pdf", 1024000);
		joinFile("D:\\temp\\copy", "D:\\temp\\copy\\tempp.pdf");
	}
}
