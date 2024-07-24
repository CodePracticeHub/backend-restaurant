package com.restaurantmanagement.menu;

import com.restaurantmanagement.entity.menu.Menu;
import com.restaurantmanagement.entity.menu.MenuRepository;
import com.restaurantmanagement.entity.menu.MenuServiceImpl;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu testMenu;

    @BeforeEach
    void setUp() {
        testMenu = new Menu(1L, "Test Menu", "Test Description", BigDecimal.valueOf(10.99), "Test Category",
                "test-image-url.jpg", new Date(System.currentTimeMillis()), null, null);
    }

    @Test
    public void testGetAllMenus() {
        List<Menu> menus = new ArrayList<>();
        menus.add(testMenu);
        Page<Menu> page = new PageImpl<>(menus);

        when(menuRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Menu> result = menuService.getAllMenus(Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals("Test Menu", result.getContent().get(0).getName());
    }

    @Test
    public void testGetMenuById() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(testMenu));

        Menu result = menuService.getMenuById(1L);

        assertNotNull(result);
        assertEquals("Test Menu", result.getName());
    }

    @Test
    public void testGetMenuById_NotFound() {
        when(menuRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuService.getMenuById(2L));
    }

    @Test
    public void testSaveMenuDetails() {
        when(menuRepository.save(any(Menu.class))).thenReturn(testMenu);

        Menu result = menuService.saveMenuDetails(testMenu);

        assertNotNull(result);
        assertEquals("Test Menu", result.getName());
    }

    @Test
    public void testDeleteMenuById() {
        // Mock repository to return testMenu when findById is called with 1L
        when(menuRepository.findById(1L)).thenReturn(Optional.of(testMenu));

        // Call the method under test
        menuService.deleteMenuById(1L);

        // Verify that delete method was called with the correct menu object
        verify(menuRepository, times(1)).delete(testMenu);
    }


    @Test
    public void testDeleteMenuById_NotFound() {
        when(menuRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuService.deleteMenuById(2L));
    }

    @Test
    public void testUpdateMenuDetails() {
        // Mock the MenuRepository
        MenuRepository mockRepository = mock(MenuRepository.class);
        Menu testMenu = new Menu(1L, "Test Menu", "Test Description", BigDecimal.valueOf(10.99), "Test Category",
                "test-image-url.jpg", new java.sql.Date(System.currentTimeMillis()), null, null); // Create a test menu object

        // Mock findById to return a test menu
        when(mockRepository.findById(1L)).thenReturn(Optional.of(testMenu));

        // Prepare the updated menu details
        Menu updatedMenu = new Menu();
        updatedMenu.setName("Updated Menu");

        // Mock the save method to return the updated menu
        when(mockRepository.save(any(Menu.class))).thenAnswer(invocation -> {
            Menu menuToSave = invocation.getArgument(0);
            return new Menu(
                    menuToSave.getMenuId(),
                    menuToSave.getName(),
                    menuToSave.getDescription(),
                    menuToSave.getPrice(),
                    menuToSave.getCategory(),
                    menuToSave.getImageURL(),
                    menuToSave.getDate(),
                    menuToSave.getCreatedAt(),
                    menuToSave.getUpdatedAt()
            );
        });

        // Create the service with the mocked repository
        MenuServiceImpl menuService = new MenuServiceImpl(mockRepository);

        // Perform the update operation
        Menu result = menuService.updateMenuDetails(1L, updatedMenu);

        // Assert that the result is not null and has the updated name
        assertNotNull(result);
        assertEquals("Updated Menu", result.getName());
    }

    @Test
    public void testUpdateMenuDetails_NotFound() {
        when(menuRepository.findById(2L)).thenReturn(Optional.empty());

        Menu updatedMenu = new Menu();
        updatedMenu.setName("Updated Menu");

        assertThrows(ResourceNotFoundException.class, () -> menuService.updateMenuDetails(2L, updatedMenu));
    }

    @Test
    public void testReadByDate() {
        List<Menu> menus = new ArrayList<>();
        menus.add(testMenu);

        when(menuRepository.findByDateBetween(any(Date.class), any(Date.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(menus));

        List<Menu> result = menuService.readByDate(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), Pageable.unpaged());

        assertEquals(1, result.size());
        assertEquals("Test Menu", result.get(0).getName());
    }
}
