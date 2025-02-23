package org.djago.service;

import java.util.List;

import org.djago.model.Product;
import org.djago.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.getProductById(id).
				orElseThrow(()->new RuntimeException("id is not found"));
	}
	
	public Product  addProduct(Product emp) {
		return productRepository.save(emp);
	}
	
	public Product  updateProduct(Product emp) {
		if(!productRepository.getProductById(emp.getId()).isPresent()) {
			throw(new RuntimeException("id is not found"));
		}
		return productRepository.save(emp);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}