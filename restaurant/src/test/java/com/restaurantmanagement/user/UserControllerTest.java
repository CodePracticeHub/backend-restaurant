package com.restaurantmanagement.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.restaurantmanagement.entity.user.IUser;
import com.restaurantmanagement.entity.user.UserController;
import com.restaurantmanagement.entity.user.UserUpdateDTO;
import com.restaurantmanagement.security.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUser userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user1.setId(1L);
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("johndoe@example.com");

        user2 = new User();
        user2.setId(2L);
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("janesmith@example.com");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[1].firstname").value("Jane"));

        verify(userService, times(1)).getUsers();
    }

    @Test
    @WithMockUser
    public void testGetUserById() throws Exception {
        when(userService.getUserByUserId(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"));

        verify(userService, times(1)).getUserByUserId(1L);
    }

    @Test
    @WithMockUser
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserByUserId(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByUserId(1L);
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setEmail("newemail@example.com");

        when(userService.updateUser(anyLong(), any(UserUpdateDTO.class))).thenReturn(user1);

        mockMvc.perform(put("/api/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"));

        verify(userService, times(1)).updateUser(anyLong(), any(UserUpdateDTO.class));
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
