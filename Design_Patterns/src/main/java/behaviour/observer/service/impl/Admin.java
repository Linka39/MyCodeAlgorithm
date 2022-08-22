package behaviour.observer.service.impl;

import behaviour.observer.entity.Product;
import behaviour.observer.service.ProductObserver;

public class Admin implements ProductObserver {

	@Override
	public void onPublished(Product product) {
		System.out.println("[Admin] on product published: " + product);
	}

	@Override
	public void onPriceChanged(Product product) {
		System.out.println("[Admin] on product price changed: " + product);
	}
}
