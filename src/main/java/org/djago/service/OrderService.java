package org.djago.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.djago.model.Order;

public interface OrderService {

	List<Order> findAll();

	Optional<Order> findOrderById(Long id);

	List<Order> findPendingOrders();

	List<Order> findConfirmedOrders();

	List<Order> findOrdersByDate(LocalDate date);

	List<Order> findOrdersByDateRange(LocalDate startDate, LocalDate endDate);
	
	Order findOrderByOrderID(Long id);

	Order findConfirmedOrderById(Long id);

	Order addOrder(Order order);
	
	Order updateOrder(Order order);

	void deleteOrderById(Long id);

//	void deleteOrder(OrderUIModel order);
	
}