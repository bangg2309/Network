package exam11;

public class Subject {
	private String name;
	private double score;

	public Subject(String name, double score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + ", score=" + score + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
