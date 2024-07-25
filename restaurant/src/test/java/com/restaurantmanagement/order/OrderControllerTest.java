package com.restaurantmanagement.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantmanagement.entity.order.IOrderService;
import com.restaurantmanagement.entity.order.Order;
import com.restaurantmanagement.entity.order.OrderController;
import com.restaurantmanagement.entity.order.EOrderStatus;
import com.restaurantmanagement.entity.order.EOrderPaymentStatus;
import com.restaurantmanagement.entity.order.dto.OrderDTO;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    private Order order;
    private OrderDTO orderDTO;
    private Map<String, Object> partialOrderUpdate;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderID(1L);
        order.setOrderDateTime(new Date());
        order.setTotalAmount(100.0);
        order.setStatus(EOrderStatus.PENDING);

        orderDTO = new OrderDTO(order);

        partialOrderUpdate = new HashMap<>();
        partialOrderUpdate.put("confirmedAt", "2024-07-25 22:03:08");
        partialOrderUpdate.put("status", "CONFIRMED");
        partialOrderUpdate.put("paymentStatus", "REFUNDED");
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(orderDTO));

        mockMvc.perform(get("/api/v1/orders/get-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderID").value(orderDTO.getOrderID()));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testPlaceOrder() throws Exception {
        when(orderService.placeOrder(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/v1/orders/place-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(orderDTO.getOrderID()));

        verify(orderService, times(1)).placeOrder(any(Order.class));
    }

    @Test
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/v1/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(orderDTO.getOrderID()));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testDeleteOrderById() throws Exception {
        doThrow(new ResourceNotFoundException("Order Not Found with ID: " + 1L)).when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/api/v1/orders/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).deleteOrderById(1L);
    }

    @Test
    void testUpdateOrder() throws Exception {
        when(orderService.updateOrder(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(put("/api/v1/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(orderDTO.getOrderID()));

        verify(orderService, times(1)).updateOrder(any(Order.class));
    }

    @Test
    void testPartialUpdateOrder() throws Exception {
        when(orderService.partialUpdateOrder(eq(1L), any(Map.class))).thenReturn(orderDTO);

        mockMvc.perform(patch("/api/v1/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(partialOrderUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(orderDTO.getOrderID()));

        verify(orderService, times(1)).partialUpdateOrder(eq(1L), any(Map.class));
    }
}
