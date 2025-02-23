package org.djago.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.djago.model.Order;
import org.djago.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


//https://www.baeldung.com/spring-data-jpa-query

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.status = :status")
	List<Order> findPendingOrders(@Param("status") OrderStatus status);

	@Query("SELECT o FROM Order o WHERE o.status = :status")
	List<Order> findConfirmedOrders(@Param("status") OrderStatus status);
	
	@Query("SELECT o FROM Order o WHERE o.orderDate = :date and o.status = :status")
	List<Order> findOrdersByDate(@Param("date") LocalDate date, OrderStatus status);
	
	@Query("SELECT o FROM Order o WHERE o.orderDate between :startDate and :endDate and o.status = :status")
	List<Order> findOrdersByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("status") OrderStatus status);
	
	@Query("SELECT o FROM Order o WHERE o.id = :id")
	Optional<Order> findOrderByOrderID(@Param("id") Long id);

	@Query("SELECT o FROM Order o WHERE o.id = :id and o.status = :status")
	Optional<Order> findConfirmedOrderById(Long id, OrderStatus status);
}