package com.restaurantmanagement.entity.order;

import com.restaurantmanagement.entity.order.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
	List<OrderDTO> getAllOrders();
	OrderDTO placeOrder(Order order);
	OrderDTO getOrderById(Long id);
	void deleteOrderById(Long id);
	OrderDTO updateOrder(Order order);
	OrderDTO updatePaymentStatus(Long orderId, EOrderPaymentStatus paymentStatus);
	OrderDTO completeOrder(Long orderId);
}
