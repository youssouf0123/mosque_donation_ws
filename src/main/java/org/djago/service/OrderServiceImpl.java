package org.djago.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.djago.model.Customer;
import org.djago.model.Order;
import org.djago.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("orderService")
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Optional<Order> findOrderById(Long id) {
		return orderRepository.findById(id);
	}

	public List<Order> findPendingOrders() {
		return orderRepository.findPendingOrders(Order.OrderStatus.PENDING);
	}

	public List<Order> findConfirmedOrders() {
		return orderRepository.findConfirmedOrders(Order.OrderStatus.CONFIRMED);
	}

	@Override
	public List<Order> findOrdersByDate(LocalDate date) {
		return orderRepository.findOrdersByDate(date, Order.OrderStatus.CONFIRMED);
	}

	@Override
	public List<Order> findOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
		return orderRepository.findOrdersByDateRange(startDate, endDate, Order.OrderStatus.CONFIRMED);
	}

	@Override
	public Order addOrder(Order order) {
		if ( orderRepository.findOrderByOrderID(order.getId()).isPresent() ) {
			logger.debug("Order already exists!");
			throw(new RuntimeException("id is found. Order exists!"));
		}
		Order savedOrder = orderRepository.save(order);
		return savedOrder;
	}

	@Override
	public Order updateOrder(Order order) {

		if (!orderRepository.findOrderByOrderID(order.getId()).isPresent()) {
			throw (new RuntimeException("id is not found"));
		}
		return orderRepository.save(order);
	}

	@Override
	public void deleteOrderById(Long id) {
		orderRepository.deleteById(id);
	}

//	@Override
//	public void deleteOrder(OrderUIModel order) {
//		orderRepository.deleteById(order.getId());
//	}

	@Override
	public Order findOrderByOrderID(Long id) {
		return orderRepository.findOrderByOrderID(id).orElseThrow(() -> new RuntimeException("id is not found"));
	}

	@Override
	public Order findConfirmedOrderById(Long id) {
		return orderRepository.findConfirmedOrderById(id, Order.OrderStatus.CONFIRMED)
				.orElseThrow(() -> new RuntimeException("id is not found"));
	}
	
}