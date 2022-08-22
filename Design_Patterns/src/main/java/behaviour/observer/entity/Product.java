package behaviour.observer.entity;

public class Product {

	private String name;
	private double price;

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("{Product: name=%s, price=%s}", name, price);
	}
}
