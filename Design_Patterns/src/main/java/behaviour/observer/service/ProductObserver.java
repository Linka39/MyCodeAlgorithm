package behaviour.observer.service;

import behaviour.observer.entity.Product;

public interface ProductObserver {

	void onPublished(Product product);

	void onPriceChanged(Product product);
}
