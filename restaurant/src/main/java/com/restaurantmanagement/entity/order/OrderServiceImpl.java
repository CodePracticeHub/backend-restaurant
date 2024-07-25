package com.restaurantmanagement.entity.order;

import com.restaurantmanagement.entity.order.dto.OrderDTO;
import com.restaurantmanagement.entity.menu.Menu;
import com.restaurantmanagement.entity.menu.MenuRepository;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<OrderDTO> getAllOrders() {
		logger.info("Inside getOrders Service ...");
		return orderRepository.findAll().stream().map(OrderDTO::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public OrderDTO placeOrder(Order order) {
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
			orderItem.setPrice(menuItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
		}

		// Calculate total amount
		order.setTotalAmount(order.getOrderItems().stream()
				.map(OrderItem::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.doubleValue());

		// Set timestamps
		order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		Order savedOrder = orderRepository.save(order);
		return new OrderDTO(savedOrder);
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		logger.info("Inside getOrderById Service ...");
		Optional<Order> optional = orderRepository.findById(id);
		Order order = optional.orElseThrow(() -> new ResourceNotFoundException("Order Not Found with ID: " + id));
		return new OrderDTO(order);
	}

	@Override
	public void deleteOrderById(Long id) {
		logger.info("Inside deleteOrderById Service ...");
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found with ID: " + id));
		orderRepository.delete(order);
	}

	@Override
	@Transactional
	public OrderDTO updateOrder(Order order) {
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
		existingOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp

		Order updatedOrder = orderRepository.save(existingOrder);
		return new OrderDTO(updatedOrder);
	}

	@Override
	@Transactional
	public OrderDTO updatePaymentStatus(Long orderId, EOrderPaymentStatus paymentStatus) {
		logger.info("Inside updatePaymentStatus Service for order ID: {}", orderId);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setPaymentStatus(paymentStatus);
		order.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp

		Order updatedOrder = orderRepository.save(order);
		return new OrderDTO(updatedOrder);
	}

	@Override
	@Transactional
	public OrderDTO completeOrder(Long orderId) {
		logger.info("Inside completeOrder Service for order ID: {}", orderId);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

		order.setOrderStatus(EOrderStatus.CONFIRMED);
		order.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp

		Order completedOrder = orderRepository.save(order);
		return new OrderDTO(completedOrder);
	}

	@Override
	@Transactional
	public OrderDTO partialUpdateOrder(Long id, Map<String, Object> updates) {
		logger.info("Inside partialUpdateOrder Service for order ID: {}", id);
		Order existingOrder = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found."));

		updates.forEach((key, value) -> {
			switch (key) {
				case "orderDateTime":
					existingOrder.setOrderDateTime(new Date((Long) value));
					break;
				case "status":
					existingOrder.setStatus(EOrderStatus.valueOf((String) value));
					break;
				case "paymentStatus":
					existingOrder.setPaymentStatus(EOrderPaymentStatus.valueOf((String) value));
					break;
				case "deliveryAddress":
					existingOrder.setDeliveryAddress((String) value);
					break;
				case "orderStatus":
					EOrderStatus newStatus = EOrderStatus.valueOf((String) value);
					existingOrder.setOrderStatus(newStatus);
					if (newStatus == EOrderStatus.CONFIRMED) {
						logger.info("Setting confirmedAt for order ID: {}", id);
						existingOrder.setConfirmedAt(new Timestamp(System.currentTimeMillis()));
					}
					break;
				case "newOrderItem":
					Map<String, Object> orderItemMap = (Map<String, Object>) value;
					Long menuId = Long.valueOf(orderItemMap.get("menuId").toString());
					int quantity = Integer.parseInt(orderItemMap.get("quantity").toString());
					Menu menuItem = menuRepository.findById(menuId)
							.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with ID: " + menuId));
					OrderItem newOrderItem = new OrderItem();
					newOrderItem.setMenuItem(menuItem);
					newOrderItem.setQuantity(quantity);
					newOrderItem.setPrice(menuItem.getPrice().multiply(new BigDecimal(quantity)));
					newOrderItem.setOrder(existingOrder);
					existingOrder.getOrderItems().add(newOrderItem);
					break;
				case "removeOrderItem":
					Long orderItemId = Long.valueOf(value.toString());
					Optional<OrderItem> orderItemOptional = existingOrder.getOrderItems().stream()
							.filter(item -> item.getId().equals(orderItemId))
							.findFirst();
					if (orderItemOptional.isPresent()) {
						OrderItem orderItemToRemove = orderItemOptional.get();
						orderItemToRemove.setOrder(null); // Break the association
						existingOrder.getOrderItems().remove(orderItemToRemove);
					} else {
						logger.warn("Order item with ID {} not found in order ID {}", orderItemId, id);
						throw new ResourceNotFoundException("Order item with ID " + orderItemId + " not found in the order.");
					}
					break;
				default:
					throw new IllegalArgumentException("Invalid field: " + key);
			}
		});

		// Recalculate total amount
		BigDecimal totalAmount = existingOrder.getOrderItems().stream()
				.map(OrderItem::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		existingOrder.setTotalAmount(totalAmount.doubleValue());

		existingOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp

		Order updatedOrder = orderRepository.save(existingOrder);
		logger.info("Updated order: {}", updatedOrder);
		return new OrderDTO(updatedOrder);
	}

}
