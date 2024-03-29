package exam13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Exam {
	public static double getNum(String pathFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));
		String line;
		double count = 0;
		while ((line = reader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				try {
					count += Integer.parseInt(st.nextToken());
				} catch (NumberFormatException e) {
					// bo qua neu khong phai so
				}
			}
		}
		return count;
	}

	public static int getWorks(String pathFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));
		String line;
		int count = 0;
		while ((line = reader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				try {
					Double.parseDouble(st.nextToken());
				} catch (NumberFormatException e) {
					count++;
				}
			}
		}

		return count;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getNum("D:\\temp\\copy\\data.txt"));
		System.out.println(getWorks("D:\\temp\\copy\\data.txt"));

	}
}
