package com.restaurantmanagement.entity.order;

import java.util.List;
import java.util.Optional;

import com.restaurantmanagement.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantmanagement.entity.menu.Menu;
import com.restaurantmanagement.entity.menu.MenuRepository;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.security.model.User;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuRepository menuRepository;

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public List<Order> getAllOrders() {
		logger.info("Inside getOrders Service ...");
		return orderRepository.findAll();
	}

	@Override
	@Transactional
	public Order placeOrder(Order order) {
		logger.info("Inside placeOrder Service ...");

		// Fetch user details
		User user = userRepository.findById(order.getUser().getId())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID: " + order.getUser().getId()));
		order.setUser(user);

		// Fetch menu item details for each order item
		for (OrderItem orderItem : order.getOrderItems()) {
			Menu menuItem = menuRepository.findById(orderItem.getMenuItem().getMenuId())
					.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with ID: " + orderItem.getMenuItem().getMenuId()));
			orderItem.setMenuItem(menuItem);
			orderItem.setOrder(order);
		}

		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long id) {
		logger.info("Inside getOrderById Service ...");
		Optional<Order> optional = orderRepository.findById(id);
		return optional.orElseThrow(() -> new ResourceNotFoundException("Order Not Found with ID: " + id));
	}

	@Override
	public void deleteOrderById(Long id) {
		logger.info("Inside deleteOrderById Service ...");
		orderRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Order updateOrder(Order order) {
		logger.info("Inside updateOrder Service ...");
		Long orderId = order.getOrderID();

		Order existingOrder = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		existingOrder.setOrderDateTime(order.getOrderDateTime());
		existingOrder.setTotalAmount(order.getTotalAmount());
		existingOrder.setStatus(order.getStatus());
		existingOrder.setPaymentStatus(order.getPaymentStatus());
		existingOrder.setDeliveryAddress(order.getDeliveryAddress());
		existingOrder.setOrderStatus(order.getOrderStatus());
		existingOrder.setConfirmedAt(order.getConfirmedAt());
		existingOrder.setCanceledAt(order.getCanceledAt());

		return orderRepository.save(existingOrder);
	}

	@Override
	@Transactional
	public Order updatePaymentStatus(Long orderId, EOrderPaymentStatus paymentStatus) {
		logger.info("Inside updatePaymentStatus Service for order ID: {}", orderId);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setPaymentStatus(paymentStatus);
		return orderRepository.save(order);
	}

	@Override
	@Transactional
	public Order completeOrder(Long orderId) {
		logger.info("Inside completeOrder Service for order ID: {}", orderId);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setOrderStatus(EOrderStatus.CONFIRMED);
		return orderRepository.save(order);
	}
}
