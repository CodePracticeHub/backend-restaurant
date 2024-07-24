package com.restaurantmanagement;

import com.restaurantmanagement.security.jwt.AuthTokenFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTokenFilterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testFilterWithValidToken() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/user/all")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2hueWRvZSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzIxODA2MzE3LCJleHAiOjE3MjQzOTgzMTd9.FScvTXpVd6fsa6K6IkSrcesr9YF6U3ApfKpWTPFRJwk");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testFilterWithoutToken() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/user/all");

        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }
}
