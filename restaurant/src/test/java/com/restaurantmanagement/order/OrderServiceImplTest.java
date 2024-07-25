package com.restaurantmanagement.order;

import com.restaurantmanagement.entity.order.*;
import com.restaurantmanagement.entity.order.dto.OrderDTO;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.security.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order order;
    private User user;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setQuantity(2);
        orderItem1.setPrice(new BigDecimal("8.99"));

        orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setQuantity(3);
        orderItem2.setPrice(new BigDecimal("1.99"));

        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);

        order = new Order();
        order.setOrderID(1L);
        order.setOrderDateTime(new Date());
        order.setTotalAmount(28.97);
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setPaymentStatus(EOrderPaymentStatus.UNPAID);
        order.setDeliveryAddress("123 Main St, Anytown, USA");
        order.setOrderStatus(EOrderStatus.PENDING);
    }

    @Test
    public void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        List<OrderDTO> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    public void testPlaceOrder() {
        when(orderRepository.save(order)).thenReturn(order);
        OrderDTO placedOrder = orderService.placeOrder(order);
        assertNotNull(placedOrder);
        assertEquals(order.getOrderID(), placedOrder.getOrderID());
    }

    @Test
    public void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        OrderDTO foundOrder = orderService.getOrderById(1L);
        assertNotNull(foundOrder);
        assertEquals(order.getOrderID(), foundOrder.getOrderID());
    }

    @Test
    public void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    public void testDeleteOrderById() {
        doNothing().when(orderRepository).deleteById(1L);
        orderService.deleteOrderById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateOrder() {
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        order.setOrderStatus(EOrderStatus.CONFIRMED);
        OrderDTO updatedOrder = orderService.updateOrder(order);
        assertNotNull(updatedOrder);
        assertEquals(EOrderStatus.CONFIRMED, updatedOrder.getOrderStatus());
    }

    @Test
    public void testUpdatePaymentStatus() {
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        OrderDTO updatedOrder = orderService.updatePaymentStatus(order.getOrderID(), EOrderPaymentStatus.PAID);
        assertNotNull(updatedOrder);
        assertEquals(EOrderPaymentStatus.PAID, updatedOrder.getPaymentStatus());
    }

    @Test
    public void testCompleteOrder() {
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        OrderDTO completedOrder = orderService.completeOrder(order.getOrderID());
        assertNotNull(completedOrder);
        assertEquals(EOrderStatus.CONFIRMED, completedOrder.getOrderStatus());
    }
}
