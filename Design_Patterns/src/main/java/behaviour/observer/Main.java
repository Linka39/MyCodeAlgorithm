package behaviour.observer;


import behaviour.observer.entity.Product;
import behaviour.observer.service.ProductObserver;
import behaviour.observer.entity.Store;
import behaviour.observer.service.impl.Admin;
import behaviour.observer.service.impl.Customer;


/**
 * Learn Java from https://www.liaoxuefeng.com/
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		// observer:
		Admin a = new Admin();
		Customer c = new Customer();
		// store:
		Store store = new Store();
		// register:
		store.addObserver(a);
		store.addObserver(c);
		// 注册匿名观察者:
		store.addObserver(new ProductObserver() {
			@Override
			public void onPublished(Product product) {
				System.out.println("[Log] on product published: " + product);
			}

			@Override
			public void onPriceChanged(Product product) {
				System.out.println("[Log] on product price changed: " + product);
			}
		});
		// operation:
		store.addNewProduct("Design Patterns", 35.6);
		store.addNewProduct("Effective Java", 50.8);
		store.setProductPrice("Design Patterns", 31.9);
	}
}


