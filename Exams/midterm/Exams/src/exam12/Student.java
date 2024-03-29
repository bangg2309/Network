package exam12;

import java.util.List;

public class Student {
	private String name;
	private int id;
	private List<Subject> subjects;

	public Student(String name, int id, List<Subject> subjects) {
		super();
		this.name = name;
		this.id = id;
		this.subjects = subjects;
	}

	public double getAverage() {
		double total = 0;
		for (Subject subject : subjects) {
			total += subject.getScore();
		}
		return total / subjects.size();
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", subjects=" + subjects + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
