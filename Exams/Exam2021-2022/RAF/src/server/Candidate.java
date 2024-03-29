package server;

import java.time.Year;
import java.util.Arrays;

public class Candidate {
	private int id;
	private String name;
	private String birth;
	private String address;
	private byte[] fileImg;

	public Candidate(int id, String name, String birth, String address) {
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.address = address;
	}

	public Candidate() {

	}

	public Candidate(String name, String birth, String address) {
		super();
		this.name = name;
		this.birth = birth;
		this.address = address;
	}

	public Candidate(int id, String name, String birth, String address, byte[] fileImg) {
		super();
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.address = address;
		this.fileImg = fileImg;
	}

	public boolean isValidAge() {
		int birthYear = Integer.parseInt(this.birth.substring(this.birth.length() - 4));
		return Integer.parseInt(Year.now().toString()) - birthYear <= 10;
	}

	@Override
	public String toString() {
		return "Candidate id=" + id + ", name=" + name + ", birth=" + birth + ", address=" + address + ", fileImg=";
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

	public byte[] getFileImg() {
		return fileImg;
	}

	public void setFileImg(byte[] fileImg) {
		this.fileImg = fileImg;
	}

}
