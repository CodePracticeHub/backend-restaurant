package com.restaurantmanagement.security.jwt;


import com.restaurantmanagement.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

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
        // Set up mocks
        String jwt = "mock.jwt.token";
        when(jwtUtils.validateJwtToken(jwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwt)).thenReturn("user");

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(mockUserDetails);

        // Simulate a request with the JWT token
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        authTokenFilter.doFilterInternal(request, response, chain);

        // Assert expected interactions
        verify(chain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("user");
    }
}
