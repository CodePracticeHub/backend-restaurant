package com.restaurantmanagement.security;


import com.restaurantmanagement.security.jwt.AuthTokenFilter;
import com.restaurantmanagement.security.jwt.JwtUtils;
import com.restaurantmanagement.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthTokenFilterTest {

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testDoFilterInternal() throws Exception {
        // Subclass to expose the protected method
        class TestAuthTokenFilter extends AuthTokenFilter {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                super.doFilterInternal(request, response, chain);
            }
        }

        // Create mock dependencies
        JwtUtils jwtUtils = mock(JwtUtils.class);
        UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);

        // Create instance of TestAuthTokenFilter and inject mocks
        TestAuthTokenFilter filter = new TestAuthTokenFilter();
        filter.setJwtUtils(jwtUtils);
        filter.setUserDetailsService(userDetailsService);

        // Mock request, response, and filter chain
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // Set up mock behavior
        request.addHeader("Authorization", "Bearer test-jwt-token");
        when(jwtUtils.validateJwtToken("test-jwt-token")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("test-jwt-token")).thenReturn("testUser");

        // Mock user details service
        var mockUserDetails = mock(org.springframework.security.core.userdetails.User.class);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mockUserDetails);

        // Execute the filter
        filter.doFilterInternal(request, response, chain);

        // Assert that the SecurityContext is set
        assertNotNull(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication());
    }


}
