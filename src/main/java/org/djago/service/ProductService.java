package org.djago.service;

import java.util.List;

import org.djago.model.Product;
import org.djago.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {

	public List<Product> findAllProducts();
	
	public Product getProductById(Long id);
	
	public Product  addProduct(Product emp);
	
	public Product  updateProduct(Product emp);
	
	public void deleteProduct(Long id);
}