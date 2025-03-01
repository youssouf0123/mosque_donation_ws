package org.djago.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.djago.model.Product;
import org.djago.service.ProductService;
import org.djago.service.ProductTypeQty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "http://mande-dev.com"}, maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProduct(){
		List<Product> prcdList = productService.findAllProducts();
		return new ResponseEntity<>(prcdList, HttpStatus.OK);
	}
	
	@GetMapping("/typeQty")
	public ResponseEntity<List<ProductTypeQty>> getProductQuantityAndType(){
		List<ProductTypeQty> prcdTypeQtyList = productService.getProductQuantityAndType();
		logger.info("Youssouf prcdTypeQtyList : " + prcdTypeQtyList);
		return new ResponseEntity<>(prcdTypeQtyList, HttpStatus.OK);
	}
	
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
		Product prcd = productService.getProductById(id);
		return new ResponseEntity<Product>(prcd, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//@RequestMapping(value = "add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> addProduct(@RequestBody Product prcd){
		 Set<String> productList = productService.findAllProducts().stream().map(Product::getName).map(x->x.toLowerCase()).collect(Collectors.toCollection(TreeSet::new));
		 boolean isProductNameExist = productList.contains(prcd.getName().toLowerCase());
		 Product newPrcd  = prcd;
		 logger.info("Youssouf isProductNameExist : " + isProductNameExist);
		 if(!isProductNameExist)
			 newPrcd = productService.addProduct(prcd);
		return new ResponseEntity<>(newPrcd, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product prcd){
		Set<String> productList = productService.findAllProducts().stream().map(Product::getName).map(x->x.toLowerCase()).collect(Collectors.toCollection(TreeSet::new));
		Map<String, Long> mapNameId = productService.findAllProducts().stream()
				.collect(Collectors.toMap(Product::getName, Product::getId));
		
		
		boolean isProductUpdatable = false;

		logger.info("Youssouf Product prcd : " + prcd);
		logger.info("Youssouf Product mapNameId : " + mapNameId);

		//This will prevent to edit a product to an existing product
		for (String str : mapNameId.keySet()) {
			if (str.equalsIgnoreCase(prcd.getName())) {
				if (mapNameId.get(str) == prcd.getId()) {
					isProductUpdatable = true;
				}
				break;
			}
		}
		
		//This will allow you to edit the same product
		if(!isProductUpdatable) {
			for (Long id : mapNameId.values()) {
				if (id == prcd.getId()) {
					if (!productList.contains(prcd.getName().toLowerCase())) {
						isProductUpdatable = true;
					}
					break;
				}
			}
		}

		Product updatePrcd = prcd;
		logger.info("Youssouf isProductNameExist : " + isProductUpdatable);
		if (isProductUpdatable) {
			updatePrcd = productService.updateProduct(prcd);
		} else {
			updatePrcd.setId(null);
		}

		return new ResponseEntity<>(updatePrcd, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}