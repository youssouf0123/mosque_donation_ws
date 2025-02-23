package org.djago.controller;

import java.util.List;

import org.djago.model.Customer;
import org.djago.service.CustomerService;
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
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomer(){
		List<Customer> prcdList = customerService.findAllCustomers();
		return new ResponseEntity<>(prcdList, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
		Customer prcd = customerService.getCustomerById(id);
		return new ResponseEntity<Customer>(prcd, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//@RequestMapping(value = "add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer prcd){
		Customer newPrcd = customerService.addCustomer(prcd);
		return new ResponseEntity<>(newPrcd, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer prcd){
			
		Customer updatePrcd = customerService.updateCustomer(prcd);
		return new ResponseEntity<>(updatePrcd, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
		customerService.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}