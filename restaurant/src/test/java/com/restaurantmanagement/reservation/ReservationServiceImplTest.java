package com.restaurantmanagement.reservation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.restaurantmanagement.entity.reservation.Reservation;
import com.restaurantmanagement.entity.reservation.ReservationRepository;
import com.restaurantmanagement.entity.reservation.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;


public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.getAllReservation();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testAddReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1);

        Reservation savedReservation = reservationService.addReservation(reservation1);

        assertNotNull(savedReservation);
        assertEquals("John Doe", savedReservation.getCustomerName());
        verify(reservationRepository, times(1)).save(reservation1);
    }

    @Test
    public void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation1));

        Reservation reservation = reservationService.getReservationById(1L);

        assertNotNull(reservation);
        assertEquals("John Doe", reservation.getCustomerName());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReservationByIdNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getReservationById(1L);
        });
    }

    @Test
    public void testDeleteReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation1));
        doNothing().when(reservationRepository).delete(reservation1);

        reservationService.deleteReservationBy(1L);

        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).delete(reservation1);
    }

    @Test
    public void testUpdateReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation1));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1);

        reservation1.setCustomerName("John Updated");

        Reservation updatedReservation = reservationService.updateReservation(reservation1);

        assertNotNull(updatedReservation);
        assertEquals("John Updated", updatedReservation.getCustomerName());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(reservation1);
    }
}
