package org.djago.repositories;

import java.util.Optional;

import org.djago.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> getCustomerById(Long id);
}