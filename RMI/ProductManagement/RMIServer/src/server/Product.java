package server;

import java.io.Serializable;

public class Product implements Serializable {

	private int idsp;
	private String name;
	private int count;
	private double price;

	public Product(int idsp, String name, int count, double price) {
		this.idsp = idsp;
		this.name = name;
		this.count = count;
		this.price = price;
	}

	public Product(String name, int count, double price) {
		super();
		this.name = name;
		this.count = count;
		this.price = price;
	}

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return " idsp=" + idsp + ", name=" + name + ", count=" + count + ", price=" + price;
	}

	public int getIdsp() {
		return idsp;
	}

	public void setIdsp(int idsp) {
		this.idsp = idsp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
