package com.restaurantmanagement.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.restaurantmanagement.entity.order.Order;
import com.restaurantmanagement.entity.order.OrderPaymentStatus;
import com.restaurantmanagement.entity.order.OrderRepository;
import com.restaurantmanagement.entity.order.OrderServiceImpl;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        order = new Order();
        order.setOrderID(1L);
        order.setOrderDateTime(new Date(System.currentTimeMillis()));
        order.setTotalAmount(100.0);
        order.setStatus(Order.OrderStatus.PENDING);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Order> orders = orderService.getAllOrders();
        assertThat(orders).hasSize(1);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testPlaceOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order placedOrder = orderService.placeOrder(order);
        assertThat(placedOrder).isNotNull();
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);
        assertThat(foundOrder).isNotNull();
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteOrderById() {
        orderService.deleteOrderById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.updateOrder(order);
        assertThat(updatedOrder).isNotNull();
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdatePaymentStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.updatePaymentStatus(1L, OrderPaymentStatus.PAID);
        assertThat(updatedOrder.getPaymentStatus()).isEqualTo(OrderPaymentStatus.PAID);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCompleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order completedOrder = orderService.completeOrder(1L);
        assertThat(completedOrder.getOrderStatus()).isEqualTo(Order.OrderStatus.CONFIRMED);
        verify(orderRepository, times(1)).save(order);
    }
}
