package com.restaurantmanagement.entity.reservation.reservationguest;

import com.restaurantmanagement.entity.reservation.reservationguest.dto.ReservationGuestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation-guests")
public class ReservationGuestController {

    @Autowired
    private ReservationGuestService reservationGuestService;

    @PostMapping
    public ResponseEntity<ReservationGuestDTO> createReservationGuest(@Validated @RequestBody ReservationGuestDTO reservationGuestDTO) {
        ReservationGuestDTO createdReservationGuest = reservationGuestService.createReservationGuest(reservationGuestDTO);
        return ResponseEntity.ok(createdReservationGuest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationGuestDTO> getReservationGuestById(@PathVariable Long id) {
        ReservationGuestDTO reservationGuestDTO = reservationGuestService.getReservationGuestById(id);
        return ResponseEntity.ok(reservationGuestDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReservationGuestDTO>> getAllReservationGuests() {
        List<ReservationGuestDTO> reservationGuests = reservationGuestService.getAllReservationGuests();
        return ResponseEntity.ok(reservationGuests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationGuestDTO> updateReservationGuest(@PathVariable Long id, @Validated @RequestBody ReservationGuestDTO reservationGuestDTO) {
        ReservationGuestDTO updatedReservationGuest = reservationGuestService.updateReservationGuest(id, reservationGuestDTO);
        return ResponseEntity.ok(updatedReservationGuest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationGuest(@PathVariable Long id) {
        reservationGuestService.deleteReservationGuest(id);
        return ResponseEntity.noContent().build();
    }
}
