package com.restaurantmanagement.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import com.restaurantmanagement.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.restaurantmanagement.security.service.UserDetailsImpl;

public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);
        // Use a 256-bit key for JWT
        jwtSecretField.set(jwtUtils, "MySuperSecretKeyForJwtThatIsLongEnoughToBeSecure");

        Field jwtExpirationMsField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        jwtExpirationMsField.setAccessible(true);
        jwtExpirationMsField.set(jwtUtils, 86400000);
    }

    @Test
    public void testGenerateJwtToken() {
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L, "username", "user@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L, "username", "user@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("username", username);
    }

    @Test
    public void testValidateJwtToken() {
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L, "username", "user@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    public void testInvalidJwtToken() {
        assertFalse(jwtUtils.validateJwtToken("invalidToken"));
    }
}
