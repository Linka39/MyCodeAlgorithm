package behaviour.observer.service.impl;


import behaviour.observer.entity.Product;
import behaviour.observer.service.ProductObserver;

public class Customer implements ProductObserver {

	@Override
	public void onPublished(Product product) {
		System.out.println("[Customer] on product published: " + product);
	}

	@Override
	public void onPriceChanged(Product product) {
		System.out.println("[Customer] on product price changed: " + product);
	}
}
