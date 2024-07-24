package com.restaurantmanagement.entity.order;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository repo;

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public List<Order> getAllOrders() {
		logger.info("Inside getOrders Service ...");
		List<Order> list = repo.findAll();
		return list;
	}

	@Override
	public Order placeOrder(Order order) {
		logger.info("Inside placeOrder Service ...");
		return repo.save(order);
	}

	@Override
	public Order getOrderById(Long id) {
		logger.info("Inside getOrderById Service ...");
		Optional<Order> optional = repo.findById(id);
		return optional.orElseThrow(() -> new ResourceNotFoundException("Order Not Found with ID: " + id));
	}

	@Override
	public void deleteOrderById(Long id) {
		logger.info("Inside deleteOrderById Service ...");
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public Order updateOrder(Order order) {
		logger.info("Inside updateOrder Service ...");
		Long orderId = order.getOrderID();

		Order existingOrder = repo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		existingOrder.setCreatedAt(order.getCreatedAt());
		existingOrder.setOrderDateTime(order.getOrderDateTime());
		existingOrder.setStatus(order.getStatus());
		existingOrder.setTotalAmount(order.getTotalAmount());
		existingOrder.setUpdatedAt(order.getUpdatedAt());

		return repo.save(existingOrder);
	}

	@Override
	@Transactional
	public Order updatePaymentStatus(Long orderId, OrderPaymentStatus paymentStatus) {
		logger.info("Inside updatePaymentStatus Service for order ID: {}", orderId);
		Order order = repo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setPaymentStatus(paymentStatus);
		return repo.save(order);
	}

	@Override
	@Transactional
	public Order completeOrder(Long orderId) {
		logger.info("Inside completeOrder Service for order ID: {}", orderId);
		Order order = repo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setOrderStatus(Order.OrderStatus.CONFIRMED);
		return repo.save(order);
	}
}
