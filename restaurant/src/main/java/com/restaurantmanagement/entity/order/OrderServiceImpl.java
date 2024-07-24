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
		return repo.findAll();
	}

	@Override
	@Transactional
	public Order placeOrder(Order order) {
		logger.info("Inside placeOrder Service ...");

		// Ensure the status is set to PENDING if not already set
		if (order.getStatus() == null) {
			order.setStatus(EOrderStatus.PENDING);
		}

		// Validate the order items
		if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
			throw new IllegalArgumentException("Order must have at least one item.");
		}

		// Ensure each order item references this order
		for (OrderItem item : order.getOrderItems()) {
			item.setOrder(order);
		}

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

		existingOrder.setOrderDateTime(order.getOrderDateTime());
		existingOrder.setTotalAmount(order.getTotalAmount());
		existingOrder.setStatus(order.getStatus());
		existingOrder.setPaymentStatus(order.getPaymentStatus());
		existingOrder.setDeliveryAddress(order.getDeliveryAddress());
		existingOrder.setOrderStatus(order.getOrderStatus());
		existingOrder.setConfirmedAt(order.getConfirmedAt());
		existingOrder.setCanceledAt(order.getCanceledAt());

		return repo.save(existingOrder);
	}

	@Override
	@Transactional
	public Order updatePaymentStatus(Long orderId, EOrderPaymentStatus paymentStatus) {
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

		order.setOrderStatus(EOrderStatus.CONFIRMED);
		return repo.save(order);
	}
}
