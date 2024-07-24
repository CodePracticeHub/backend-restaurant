package com.restaurantmanagement.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantmanagement.entity.menu.IMenuService;
import com.restaurantmanagement.entity.menu.Menu;
import com.restaurantmanagement.entity.menu.MenuController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(MenuController.class)
public class MenuControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMenuService menuService;

    @Test
    public void testGetMenuById() throws Exception {
        Menu testMenu = new Menu(1L, "Test Menu", "Test Description", BigDecimal.valueOf(10.99), "Test Category",
                "test-image-url.jpg", new Date(System.currentTimeMillis()), null, null);

        when(menuService.getMenuById(1L)).thenReturn(testMenu);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/menus/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Menu"));
    }

    @Test
    public void testSaveMenuDetails() throws Exception {
        Menu testMenu = new Menu(1L, "Test Menu", "Test Description", BigDecimal.valueOf(10.99), "Test Category",
                "test-image-url.jpg", new Date(System.currentTimeMillis()), null, null);

        when(menuService.saveMenuDetails(any(Menu.class))).thenReturn(testMenu);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testMenu)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Menu"));
    }

    @Test
    public void testUpdateMenuDetails() throws Exception {
        Menu updatedMenu = new Menu(1L, "Updated Menu", "Updated Description", BigDecimal.valueOf(15.99),
                "Updated Category", "updated-image-url.jpg", new Date(System.currentTimeMillis()), null, null);

        when(menuService.updateMenuDetails(any(Long.class), any(Menu.class))).thenReturn(updatedMenu);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/menus/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedMenu)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Menu"));
    }

    @Test
    public void testDeleteMenuById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/menus/1"))
                .andExpect(status().isNoContent());
    }
}
