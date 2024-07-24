package com.restaurantmanagement.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantmanagement.entity.order.IOrderService;
import com.restaurantmanagement.entity.order.Order;
import com.restaurantmanagement.entity.order.OrderController;
import com.restaurantmanagement.entity.order.EOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderID(1L);
        order.setOrderDateTime(new Date());
        order.setTotalAmount(100.0);
        order.setStatus(EOrderStatus.PENDING);
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));

        mockMvc.perform(get("/api/v1/orders/get-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderID").value(order.getOrderID()));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testPlaceOrder() throws Exception {
        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/v1/orders/place-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(order.getOrderID()));

        verify(orderService, times(1)).placeOrder(any(Order.class));
    }

    @Test
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/v1/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(order.getOrderID()));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testDeleteOrderById() throws Exception {
        doNothing().when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/api/v1/orders/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrderById(1L);
    }

    @Test
    void testUpdateOrder() throws Exception {
        when(orderService.updateOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/api/v1/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(order.getOrderID()));

        verify(orderService, times(1)).updateOrder(any(Order.class));
    }
}
