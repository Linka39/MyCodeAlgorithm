package behaviour.observer.entity;

import behaviour.observer.service.ProductObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {

	private List<ProductObserver> observers = new ArrayList<>();
	private Map<String, Product> products = new HashMap<>();

	// 注册观察者:
	public void addObserver(ProductObserver observer) {
		this.observers.add(observer);
	}

	// 取消注册:
	public void removeObserver(ProductObserver observer) {
		this.observers.remove(observer);
	}

	public void addNewProduct(String name, double price) {
		Product p = new Product(name, price);
		products.put(p.getName(), p);
		// 通知观察者:
		this.observers.forEach(o -> o.onPublished(p));
	}

	public void setProductPrice(String name, double price) {
		Product p = products.get(name);
		p.setPrice(price);
		// 通知观察者:
		this.observers.forEach(o -> o.onPriceChanged(p));
	}

	//	Customer customer;
	//	Admin admin;
	//
	//	private Map<String, Product> products = new HashMap<>();
	//
	//	public void addNewProduct(String name, double price) {
	//		Product p = new Product(name, price);
	//		products.put(p.getName(), p);
	//		// 通知用户:
	//		customer.onPublished(p);
	//		// 通知管理员:
	//		admin.onPublished(p);
	//	}
	//
	//	public void setProductPrice(String name, double price) {
	//		Product p = products.get(name);
	//		p.setPrice(price);
	//		// 通知用户:
	//		customer.onPriceChanged(p);
	//		// 通知管理员:
	//		admin.onPriceChanged(p);
	//	}
}
