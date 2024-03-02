package com.restaurantmanagement.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.controller.OrderController;
import com.restaurantmanagement.entity.CustomerOrder;
import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.repos.OrderRepository;

@Service
public class OrderService implements IOrderService {

	
	@Autowired
	private OrderRepository repo;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Override
	public List<CustomerOrder> getAllOrders() {
		logger.info("Inside getOrders Service ...");
		List<CustomerOrder> list = repo.findAll();
		return list;
	}

	@Override
	public CustomerOrder placeOrder(CustomerOrder order) {
		logger.info("Inside placeOrder Service ...");
		return repo.save(order);
		
	}
	
	
	@Override
	public CustomerOrder getOrderById(Long id) {
		logger.info("Inside getOrderById Service ...");
		Optional<CustomerOrder> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return optional.orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found!"));
		}
	}
	
	@Override
	public void deleteOrderById(Long id) {
		logger.info("Inside deleteOrderById Service ...");
		Optional<CustomerOrder> optional = repo.findById(id);
		if(optional.isPresent()) {
			CustomerOrder order = optional.get();
			repo.delete(order);
		}
	}

	@Override
	public CustomerOrder updateOrder(CustomerOrder order) {
		
		logger.info("Inside updateOrder Service ...");
		Long orderId = order.getOrderID();

		CustomerOrder existingOrder = repo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order with ID " +orderId+ " not found."));

		existingOrder.setCreatedAt(order.getCreatedAt());
		existingOrder.setOrderDateTime(order.getOrderDateTime());
		existingOrder.setStatus(order.getStatus());
		existingOrder.setTotalAmount(order.getTotalAmount());
		existingOrder.setUpdatedAt(order.getUpdatedAt());
		
		return repo.save(existingOrder);
	}
	
	
}
