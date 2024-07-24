package com.restaurantmanagement.entity.seating;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SeatingController.class)
public class SeatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISeatingService seatingService;

    private Seating seating;

    @BeforeEach
    public void setUp() {
        seating = new Seating(1L, 4, "Near the window", true, LocalDateTime.now(), null);
    }

    @Test
    public void testGetAllSeating() throws Exception {
        Page<Seating> page = new PageImpl<>(List.of(seating));
        Pageable pageable = PageRequest.of(0, 10);
        when(seatingService.getAllSeating(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/seating")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSeatingById() throws Exception {
        when(seatingService.getSeatingById(anyLong())).thenReturn(seating);

        mockMvc.perform(get("/api/v1/seating/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSeatingById() throws Exception {
        mockMvc.perform(delete("/api/v1/seating/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSaveSeating() throws Exception {
        when(seatingService.saveSeating(any(Seating.class))).thenReturn(seating);

        mockMvc.perform(post("/api/v1/seating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"capacity\": 4, \"positionDescription\": \"Near the window\", \"isAvailable\": true }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSeating() throws Exception {
        when(seatingService.updateSeating(any(Seating.class))).thenReturn(seating);

        mockMvc.perform(put("/api/v1/seating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"seatingId\": 1, \"capacity\": 4, \"positionDescription\": \"Near the window\", \"isAvailable\": true }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSeatingByAvailability() throws Exception {
        Page<Seating> page = new PageImpl<>(List.of(seating));
        Pageable pageable = PageRequest.of(0, 10);
        when(seatingService.findByisAvailable(eq(true), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/seating/available/{isAvailable}", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
