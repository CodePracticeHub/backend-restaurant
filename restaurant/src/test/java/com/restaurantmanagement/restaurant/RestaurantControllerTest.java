package com.restaurantmanagement.restaurant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.restaurantmanagement.RestaurantTestApplication;
import com.restaurantmanagement.entity.restaurant.Restaurant;
import com.restaurantmanagement.entity.restaurant.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;

import java.util.Arrays;

@SpringBootTest(classes = RestaurantTestApplication.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Page<Restaurant> restaurantPage;

    @BeforeEach
    public void setUp() {
        restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1L);
        restaurant1.setName("Restaurant One");
        restaurant1.setAddress("Address One");
        restaurant1.setContactPhone("1234567890");
        restaurant1.setOpeningHours("10:00");
        restaurant1.setClosingHours("22:00");
        restaurant1.setDescription("Description One");
        restaurant1.setCuisineType("Cuisine One");
        restaurant1.setIsAvailable(true);

        restaurant2 = new Restaurant();
        restaurant2.setRestaurantId(2L);
        restaurant2.setName("Restaurant Two");
        restaurant2.setAddress("Address Two");
        restaurant2.setContactPhone("0987654321");
        restaurant2.setOpeningHours("11:00");
        restaurant2.setClosingHours("23:00");
        restaurant2.setDescription("Description Two");
        restaurant2.setCuisineType("Cuisine Two");
        restaurant2.setIsAvailable(false);

        restaurantPage = new PageImpl<>(Arrays.asList(restaurant1, restaurant2));
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantService.getAllRestaurants(pageable)).thenReturn(restaurantPage);

        mockMvc.perform(get("/api/v1/restaurant")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Restaurant One"))
                .andExpect(jsonPath("$.content[1].name").value("Restaurant Two"));

        verify(restaurantService, times(1)).getAllRestaurants(pageable);
    }

    @Test
    public void testGetRestaurantById() throws Exception {
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant1);

        mockMvc.perform(get("/api/v1/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurant One"));

        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    public void testGetRestaurantByIdNotFound() throws Exception {
        when(restaurantService.getRestaurantById(1L)).thenThrow(new ResourceNotFoundException("Restaurant Not Found"));

        mockMvc.perform(get("/api/v1/restaurant/1"))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    public void testDeleteRestaurantById() throws Exception {
        doNothing().when(restaurantService).deleteRestaurantById(1L);

        mockMvc.perform(delete("/api/v1/restaurant/1"))
                .andExpect(status().isNoContent());

        verify(restaurantService, times(1)).deleteRestaurantById(1L);
    }

    @Test
    public void testSaveRestaurant() throws Exception {
        when(restaurantService.saveRestaurant(any(Restaurant.class))).thenReturn(restaurant1);

        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurant1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Restaurant One"));

        verify(restaurantService, times(1)).saveRestaurant(any(Restaurant.class));
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        when(restaurantService.updateRestaurant(any(Restaurant.class))).thenReturn(restaurant1);

        mockMvc.perform(put("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurant1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurant One"));

        verify(restaurantService, times(1)).updateRestaurant(any(Restaurant.class));
    }

    @Test
    public void testGetfindByisAvailable() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantService.findByisAvailable(true, pageable)).thenReturn(restaurantPage);

        mockMvc.perform(get("/api/v1/restaurant/available/true")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Restaurant One"))
                .andExpect(jsonPath("$.content[1].name").value("Restaurant Two"));

        verify(restaurantService, times(1)).findByisAvailable(true, pageable);
    }
}

