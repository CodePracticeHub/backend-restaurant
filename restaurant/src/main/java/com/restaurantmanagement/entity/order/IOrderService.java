package com.restaurantmanagement.entity.order;

import com.restaurantmanagement.entity.order.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface IOrderService {
	List<OrderDTO> getAllOrders();
	OrderDTO placeOrder(Order order);
	OrderDTO getOrderById(Long id);
	void deleteOrderById(Long id);
	OrderDTO updateOrder(Order order);
	OrderDTO updatePaymentStatus(Long orderId, EOrderPaymentStatus paymentStatus);
	OrderDTO completeOrder(Long orderId);
	OrderDTO partialUpdateOrder(Long id, Map<String, Object> updates);
}
