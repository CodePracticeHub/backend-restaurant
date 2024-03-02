package com.restaurantmanagement.service;

import java.util.List;

import com.restaurantmanagement.entity.CustomerOrder;
import com.restaurantmanagement.entity.Reservation;

public interface IOrderService {
	
	public List<CustomerOrder> getAllOrders();
	
	public CustomerOrder placeOrder(CustomerOrder order);
	
	public CustomerOrder getOrderById(Long id);

	void deleteOrderById(Long id); 
	
	CustomerOrder updateOrder(CustomerOrder order);
}
