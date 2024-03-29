package exam11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StudentManagement {

	public static void writeToFile(String pathFile, List<Student> students) throws IOException {
		File file = new File(pathFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		for (Student student : students) {
			writer.print(student.getId());
			writer.print("\t");
			writer.print(student.getName());
			writer.print("\t");
			for (Subject subject : student.getSubject()) {
				writer.print(subject.getName());
				writer.print("\t");
				writer.print(Double.toString(subject.getScore()));
				writer.print("\t");
			}
			writer.println();
		}
		writer.close();
	}

	public static List<Student> readToFile(String pathFile) throws IOException {
		File file = new File(pathFile);
		if (!file.exists() || !file.isFile()) {

			return null;
		}
		List<Student> students = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t");
			int id = Integer.parseInt(st.nextToken());
			String studentName = st.nextToken();
			int countTokens = st.countTokens();
			List<Subject> subjects = new ArrayList<>();
			while (st.hasMoreTokens()) {
				String subjectName = st.nextToken();
				double score = Double.parseDouble(st.nextToken());
				Subject subject = new Subject(subjectName, score);
				subjects.add(subject);
			}
			Student std = new Student(studentName, id, subjects);
			students.add(std);
		}
		reader.close();

		return students;
	}

	public static void printAverage(String pathFile) throws IOException {
		List<Student> students = readToFile(pathFile);
		for (Student student : students) {
			System.out.println(student.getId() + "\t" + student.getName() + "\t" + student.getAverageScore());
		}
	}

	public static void main(String[] args) throws IOException {
//		List<Subject> subjects = new ArrayList<>();
//		Subject s1 = new Subject("Toan", 10);
//		Subject s2 = new Subject("Van", 9.5);
//		subjects.add(s1);
//		subjects.add(s2);
//		Student st1 = new Student("Nguyen Van A", 1, subjects);
//		Student st2 = new Student("Nguyen Van B", 1, subjects);
//		Student st3 = new Student("Nguyen Van C", 1, subjects);
//		List<Student> students = new ArrayList<>();
//		students.add(st1);
//		students.add(st2);
//		students.add(st3);
//		String pathFile = "D:\\temp\\student.txt";
//		writeToFile(pathFile, students);

		String pathFile = "D:\\temp\\student.txt";
		printAverage(pathFile);

	}

}
