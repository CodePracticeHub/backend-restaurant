package com.restaurantmanagement.entity.order;

import java.util.List;

public interface IOrderService {

	List<Order> getAllOrders();

	Order placeOrder(Order order);

	Order getOrderById(Long id);

	void deleteOrderById(Long id);

	Order updateOrder(Order order);

	Order updatePaymentStatus(Long orderId, OrderPaymentStatus paymentStatus);

	Order completeOrder(Long orderId);
}
