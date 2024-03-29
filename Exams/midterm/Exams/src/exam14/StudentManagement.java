package exam14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StudentManagement {
	public static List<Student> loadData(String fileStudent, String fileGrade) throws IOException {
		List<Student> students = new ArrayList<>();
		BufferedReader readerStudent = new BufferedReader(
				new InputStreamReader(new FileInputStream(fileStudent), "UTF-8"));
		BufferedReader readerGrade = new BufferedReader(new InputStreamReader(new FileInputStream(fileGrade), "UTF-8"));

		int id;
		String name;
		int age;
		// Dòng đầu chứa tên các trường thuộc tính nên bỏ qua
		String lineStudent = readerStudent.readLine();

		while ((lineStudent = readerStudent.readLine()) != null) {
			StringTokenizer stStudent = new StringTokenizer(lineStudent, "\t");
			id = Integer.parseInt(stStudent.nextToken());
			name = stStudent.nextToken();
			age = Integer.parseInt(stStudent.nextToken());
			Student student = new Student(id, name, age);
			students.add(student);
		}
		readerStudent.close();

		// Dòng đầu chứa tên các trường thuộc tính nên bỏ qua
		String lineGrade = readerGrade.readLine();
		while ((lineGrade = readerGrade.readLine()) != null) {
			StringTokenizer stGrade = new StringTokenizer(lineGrade, "\t");
			id = Integer.parseInt(stGrade.nextToken());
			int countTokens = stGrade.countTokens();
			double grade = 0;
			double total = 0;
			while (stGrade.hasMoreTokens()) {
				total += Double.parseDouble(stGrade.nextToken());
			}
			for (Student st : students) {
				if (st.getId() == id) {
					st.setGrade(total / countTokens);
					break;
				}
			}
		}
		readerGrade.close();
		return students;
	}

	public static void main(String[] args) throws IOException {
		List<Student> students = loadData("D:\\temp\\copy\\sinhvien.txt", "D:\\temp\\copy\\diem.txt");
		System.out.println(students.toString());
	}
}
