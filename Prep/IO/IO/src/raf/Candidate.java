package raf;

public class Candidate {
	private int id;
	private String name;
	private String birth;
	private String address;
	private int sizeImage;

	public Candidate(int id, String name, String birth, String address, int sizeImage) {
		super();
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.address = address;
		this.sizeImage = sizeImage;
	}
	

	

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", name=" + name + ", birth=" + birth + ", address=" + address + ", sizeImage="
				+ sizeImage + "]";
	}




	public Candidate(String name, String birth, String address) {
		super();
		this.name = name;
		this.birth = birth;
		this.address = address;
	}


	public Candidate() {
		super();
	}

	public boolean isValidBirth(int yearNow) {
		int year = Integer.parseInt(birth.substring(this.birth.length() - 4));
		return yearNow - year <= 10;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSizeImage() {
		return sizeImage;
	}

	public void setSizeImage(int sizeImage) {
		this.sizeImage = sizeImage;
	}

}
