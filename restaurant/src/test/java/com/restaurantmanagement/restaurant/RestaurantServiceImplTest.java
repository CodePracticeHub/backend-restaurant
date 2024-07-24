package com.restaurantmanagement.entity.restaurant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Page<Restaurant> restaurantPage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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
    public void testGetAllRestaurants() {
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);

        Page<Restaurant> result = restaurantService.getAllRestaurants(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(restaurantRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetRestaurantById() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));

        Restaurant result = restaurantService.getRestaurantById(1L);

        assertNotNull(result);
        assertEquals("Restaurant One", result.getName());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetRestaurantByIdNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRestaurantById(1L);
        });

        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteRestaurantById() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));

        restaurantService.deleteRestaurantById(1L);

        verify(restaurantRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRestaurantByIdNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.deleteRestaurantById(1L);
        });

        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        Restaurant result = restaurantService.saveRestaurant(restaurant1);

        assertNotNull(result);
        assertEquals("Restaurant One", result.getName());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testUpdateRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        Restaurant result = restaurantService.updateRestaurant(restaurant1);

        assertNotNull(result);
        assertEquals("Restaurant One", result.getName());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testFindByisAvailable() {
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantRepository.findByisAvailable(true, pageable)).thenReturn(restaurantPage);

        Page<Restaurant> result = restaurantService.findByisAvailable(true, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(restaurantRepository, times(1)).findByisAvailable(true, pageable);
    }
}
