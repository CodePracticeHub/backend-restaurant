package com.restaurantmanagement.entity.reservation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationServiceImpl reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setUp() {
        reservation1 = new Reservation();
        reservation1.setReservationId(1L);
        reservation1.setCustomerName("John Doe");
        reservation1.setCustomerEmail("john.doe@example.com");
        reservation1.setCustomerPhone("1234567890");
        reservation1.setReservationDateTime(LocalDateTime.now());
        reservation1.setNumberOfPeople(4);
        reservation1.setTableNumber(5);
        reservation1.setStatus(Reservation.ReservationStatus.PENDING);
        reservation1.setSpecialRequest("Window seat");

        reservation2 = new Reservation();
        reservation2.setReservationId(2L);
        reservation2.setCustomerName("Jane Smith");
        reservation2.setCustomerEmail("jane.smith@example.com");
        reservation2.setCustomerPhone("0987654321");
        reservation2.setReservationDateTime(LocalDateTime.now().plusDays(1));
        reservation2.setNumberOfPeople(2);
        reservation2.setTableNumber(3);
        reservation2.setStatus(Reservation.ReservationStatus.CONFIRMED);
        reservation2.setSpecialRequest("Near the bar");
    }

    @Test
    public void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservation()).thenReturn(Arrays.asList(reservation1, reservation2));

        mockMvc.perform(get("/api/v1/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[1].customerName").value("Jane Smith"));

        verify(reservationService, times(1)).getAllReservation();
    }

    @Test
    public void testAddReservation() throws Exception {
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation1);

        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));

        verify(reservationService, times(1)).addReservation(any(Reservation.class));
    }

    @Test
    public void testGetReservationById() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(reservation1);

        mockMvc.perform(get("/api/v1/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    public void testGetReservationByIdNotFound() throws Exception {
        when(reservationService.getReservationById(1L)).thenThrow(new ResourceNotFoundException("Reservation Not Found"));

        mockMvc.perform(get("/api/v1/reservations/1"))
                .andExpect(status().isNotFound());

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    public void testDeleteReservationById() throws Exception {
        doNothing().when(reservationService).deleteReservationBy(1L);

        mockMvc.perform(delete("/api/v1/reservations/1"))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservationBy(1L);
    }

    @Test
    public void testUpdateReservationById() throws Exception {
        when(reservationService.updateReservation(any(Reservation.class))).thenReturn(reservation1);

        mockMvc.perform(put("/api/v1/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));

        verify(reservationService, times(1)).updateReservation(any(Reservation.class));
    }
}
