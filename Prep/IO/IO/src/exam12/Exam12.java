package exam12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exam12 {
	private static void writeToFile(String pathFile, List<Student> students) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathFile));
		dos.writeInt(students.size());
		for (Student student : students) {
			dos.writeInt(student.getId());
			dos.writeUTF(student.getName());
			dos.writeInt(student.getCourses().size());
			for (Course course : student.getCourses()) {
				dos.writeUTF(course.getName());
				dos.writeDouble(course.getScore());
			}
		}
		dos.close();
	}

	private static List<Student> loadFile(String pathFile) throws IOException {
		List<Student> students = new ArrayList<>();
		DataInputStream dis = new DataInputStream(new FileInputStream(pathFile));
		int sizeStudent = dis.readInt();
		int id;
		String nameStudent;
		for (int i = 0; i < sizeStudent; i++) {
			id = dis.readInt();
			nameStudent = dis.readUTF();
			Student student = new Student(id, nameStudent);
			int sizeCourse = dis.readInt();
			List<Course> courses = new ArrayList<>();
			for (int j = 0; j < sizeCourse; j++) {
				String nameCourse = dis.readUTF();
				double score = dis.readDouble();
				Course course = new Course(nameCourse, score);
				courses.add(course);
			}
			student.setCourses(courses);
			students.add(student);
		}

		return students;
	}

	public static void main(String[] args) throws IOException {
		List<Student> students = new ArrayList<>();
		List<Course> courses = new ArrayList<>();
		Student st1 = new Student(1, "Nguyen Van A");
		Student st2 = new Student(1, "Trần Van B");
		Student st3 = new Student(1, "Nguyễn Văn C");

		Course c1 = new Course("Toán", 6);
		Course c2 = new Course("Toán", 7);
		Course c3 = new Course("Toán", 8);
		courses.add(c1);
		courses.add(c2);
		courses.add(c3);
		st1.setCourses(courses);
		st2.setCourses(courses);
		st3.setCourses(courses);
		students.add(st1);
		students.add(st2);
		students.add(st3);

		String filePath = "D:\\temp\\copy\\exam12.txt";
		writeToFile(filePath, students);

		List<Student> rs = loadFile(filePath);
		for (Student student : rs) {
			System.out.println(student.toString());
		}
	}
}
