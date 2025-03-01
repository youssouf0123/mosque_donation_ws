package org.djago.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.djago.model.Product;
import org.djago.service.ProductTypeQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> getProductById(Long id);
    @Query("SELECT p.donation_type AS donationType, SUM(p.quantity) AS totalQuantity FROM Product p GROUP BY p.donation_type")
    List<ProductTypeQty> getProductQuantityAndType();
}