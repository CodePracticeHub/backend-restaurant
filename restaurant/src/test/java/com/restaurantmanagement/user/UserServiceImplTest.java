package com.restaurantmanagement.entity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.repository.UserRepository;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
    }

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(anyLong());
    }

    @Test
    public void testGetUserByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.getUserByUserId(1L);

        assertEquals(user, result);
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void testGetUserByUserIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserServiceImpl.UserNotFoundException.class, () -> {
            userService.getUserByUserId(1L);
        });

        verify(userRepository).findById(anyLong());
    }

    @Test
    public void testGetUserByUserName() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        User result = userService.getUserByUserName("johndoe");

        assertEquals(user, result);
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    public void testGetUserByUserNameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserByUserName("johndoe");
        });

        verify(userRepository).findByUsername(anyString());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");
        updatedUser.setUsername("janesmith");
        updatedUser.setEmail("janesmith@example.com");

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("janesmith", result.getUsername());
        assertEquals("janesmith@example.com", result.getEmail());

        verify(userRepository).findById(anyLong());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");
        updatedUser.setUsername("janesmith");
        updatedUser.setEmail("janesmith@example.com");

        assertThrows(UserServiceImpl.UserNotFoundException.class, () -> {
            userService.updateUser(1L, updatedUser);
        });

        verify(userRepository).findById(anyLong());
    }
}
