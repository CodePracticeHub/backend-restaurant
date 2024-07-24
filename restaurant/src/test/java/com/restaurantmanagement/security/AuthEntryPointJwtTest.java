package com.restaurantmanagement.security.jwt;

import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;

public class AuthEntryPointJwtTest {

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCommence() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/test");

        authEntryPointJwt.commence(request, response, authException);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
