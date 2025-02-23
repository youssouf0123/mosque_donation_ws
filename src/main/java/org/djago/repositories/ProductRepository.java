package org.djago.repositories;

import java.util.Optional;

import org.djago.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> getProductById(Long id);
}