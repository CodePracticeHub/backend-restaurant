package com.restaurantmanagement.entity.order;

import com.restaurantmanagement.entity.order.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	private final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@GetMapping("/get-orders")
	public List<OrderDTO> getAllOrders() {
		logger.info("Request for getting all orders ... ");
		return orderService.getAllOrders();
	}

	@PostMapping("/place-order")
	public OrderDTO placeOrder(@RequestBody Order order) {
		logger.info("Request for adding order ...");
		order.setStatus(EOrderStatus.PENDING);
		return orderService.placeOrder(order);
	}

	@GetMapping("/{id}")
	public OrderDTO getOrderById(@PathVariable Long id) {
		logger.info("Request for Order with id: {}", id);
		return orderService.getOrderById(id);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
		orderService.deleteOrderById(orderId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public OrderDTO updateOrderById(@PathVariable Long id, @RequestBody Order updatedOrder) {
		logger.info("Request for order update");
		if (!id.equals(updatedOrder.getOrderID())) {
			throw new IllegalArgumentException("Path variable ID and request body ID do not match");
		}
		return orderService.updateOrder(updatedOrder);
	}

	@PatchMapping("/{id}")
	public OrderDTO partiallyUpdateOrder(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		logger.info("Request for partial order update");
		return orderService.partialUpdateOrder(id, updates);
	}
}
