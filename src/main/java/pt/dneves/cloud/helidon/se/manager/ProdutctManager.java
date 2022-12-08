package pt.dneves.cloud.helidon.se.manager;

import pt.dneves.cloud.helidon.se.model.Product;

public class ProdutctManager {

	public static Product getProduct(int id) {
		return new Product(id, "name " + id);
	}
	
}
