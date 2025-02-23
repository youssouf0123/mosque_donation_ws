package org.djago.service;

import java.util.List;

import org.djago.model.Customer;

public interface CustomerService {

	public List<Customer> findAllCustomers();

	public Customer getCustomerById(Long id);

	public Customer addCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public void deleteCustomer(Long id);
}