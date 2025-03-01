package org.djago.service;

import java.util.List;

import org.djago.model.Product;


public interface ProductService {

	public List<Product> findAllProducts();
	
	public List<ProductTypeQty> getProductQuantityAndType();
	
	public Product getProductById(Long id);
	
	public Product  addProduct(Product emp);
	
	public Product  updateProduct(Product emp);
	
	public void deleteProduct(Long id);
}