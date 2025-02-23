package org.djago.service;

import java.util.List;

import org.djago.model.Customer;
import org.djago.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public List<Customer> findAllCustomers(){
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(Long id) {
		return customerRepository.getCustomerById(id).
				orElseThrow(()->new RuntimeException("id is not found"));
	}
	
	public Customer  addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer  updateCustomer(Customer customer) {
		if(!customerRepository.getCustomerById(customer.getId()).isPresent()) {
			throw(new RuntimeException("id is not found"));
		}
		return customerRepository.save(customer);
	}
	
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
}