package exam12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement {

	public static void writeToFile(String pathFile, List<Student> students) throws IOException {

		DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathFile));
		int size = students.size();
		dos.writeInt(size);
		for (Student student : students) {
			dos.writeInt(student.getId());
			dos.writeUTF(student.getName());
			int sizeSubject = student.getSubjects().size();
			dos.writeInt(sizeSubject);
			for (Subject subject : student.getSubjects()) {
				dos.writeUTF(subject.getName());
				dos.writeDouble(subject.getScore());
			}
		}
		dos.close();
	}

	public static List<Student> readToFile(String pathFile) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(pathFile));
		List<Student> students = new ArrayList<>();
		int size = dis.readInt();
		for (int i = 0; i < size; i++) {
			int id = dis.readInt();
			System.out.println(id);
			String name = dis.readUTF();
			int sizeSubject = dis.readInt();
			List<Subject> subjects = new ArrayList<>();
			for (int j = 0; j < sizeSubject; j++) {
				String subjectName = dis.readUTF();
				double score = dis.readDouble();
				Subject subject = new Subject(subjectName, score);
				subjects.add(subject);
			}
			Student student = new Student(name, id, subjects);
			students.add(student);
		}
		dis.close();
		return students;
	}

	public static void getAvarageStudents(String pathFile) throws IOException {
		List<Student> students = readToFile(pathFile);
		for (Student student : students) {
			System.out.println("ID: " + student.getId() + "\t" + "Ho va ten: " + student.getName() + "\t" + "DTB: "
					+ student.getAverage());
		}
	}

	public static void main(String[] args) throws IOException {
		List<Subject> subjects = new ArrayList<>();
		Subject s1 = new Subject("Toan", 10);
		Subject s2 = new Subject("Van", 9.5);
		subjects.add(s1);
		subjects.add(s2);
		Student st1 = new Student("Nguyen Van A", 1, subjects);
		Student st2 = new Student("Nguyen Van B", 1, subjects);
		Student st3 = new Student("Nguyen Van C", 1, subjects);
		List<Student> students = new ArrayList<>();
		students.add(st1);
		students.add(st2);
		students.add(st3);
		String pathFile = "D:\\temp\\student.txt";
		writeToFile(pathFile, students);

		getAvarageStudents(pathFile);

	}

}
