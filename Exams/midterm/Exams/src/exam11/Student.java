package exam11;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String name;
	private int id;
	private List<Subject> subject;

	public Student(String name, int id, List<Subject> subject) {
		super();
		this.name = name;
		this.id = id;
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", subject=" + subject + "]";
	}

	public double getAverageScore() {
		double total = 0;
		for (Subject item : subject) {
			total += item.getScore();
		}
		return total / subject.size();
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

	public List<Subject> getSubject() {
		return subject;
	}

	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}

}
