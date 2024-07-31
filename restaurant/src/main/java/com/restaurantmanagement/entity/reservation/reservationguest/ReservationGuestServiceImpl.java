package com.restaurantmanagement.entity.reservation.reservationguest;

import com.restaurantmanagement.entity.reservation.reservationguest.dto.ReservationGuestDTO;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationGuestServiceImpl implements ReservationGuestService {

    @Autowired
    private ReservationGuestRepository reservationGuestRepository;

    @Override
    @Transactional
    public ReservationGuestDTO createReservationGuest(ReservationGuestDTO reservationGuestDTO) {
        ReservationGuest reservationGuest = new ReservationGuest();
        // Copy properties from DTO to entity
        reservationGuest.setName(reservationGuestDTO.getName());
        reservationGuest.setEmail(reservationGuestDTO.getEmail());
        reservationGuest.setContactNumber(reservationGuestDTO.getContactNumber());
        reservationGuest.setCardNumber(reservationGuestDTO.getPaymentInformation().getCardNumber());
        reservationGuest.setCardHolderName(reservationGuestDTO.getPaymentInformation().getCardHolderName());
        reservationGuest.setCardExpiryDate(reservationGuestDTO.getPaymentInformation().getCardExpiryDate());
        reservationGuest.setCardCVV(reservationGuestDTO.getPaymentInformation().getCardCVV());

        ReservationGuest savedGuest = reservationGuestRepository.save(reservationGuest);
        return new ReservationGuestDTO(savedGuest);
    }

    @Override
    public ReservationGuestDTO getReservationGuestById(Long id) {
        ReservationGuest reservationGuest = reservationGuestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReservationGuest not found with ID: " + id));
        return new ReservationGuestDTO(reservationGuest);
    }

    @Override
    public List<ReservationGuestDTO> getAllReservationGuests() {
        return reservationGuestRepository.findAll().stream()
                .map(ReservationGuestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationGuestDTO updateReservationGuest(Long id, ReservationGuestDTO reservationGuestDTO) {
        ReservationGuest existingGuest = reservationGuestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReservationGuest not found with ID: " + id));

        // Update properties from DTO to entity
        existingGuest.setName(reservationGuestDTO.getName());
        existingGuest.setEmail(reservationGuestDTO.getEmail());
        existingGuest.setContactNumber(reservationGuestDTO.getContactNumber());
        existingGuest.setCardNumber(reservationGuestDTO.getPaymentInformation().getCardNumber());
        existingGuest.setCardHolderName(reservationGuestDTO.getPaymentInformation().getCardHolderName());
        existingGuest.setCardExpiryDate(reservationGuestDTO.getPaymentInformation().getCardExpiryDate());
        existingGuest.setCardCVV(reservationGuestDTO.getPaymentInformation().getCardCVV());

        ReservationGuest updatedGuest = reservationGuestRepository.save(existingGuest);
        return new ReservationGuestDTO(updatedGuest);
    }

    @Override
    @Transactional
    public void deleteReservationGuest(Long id) {
        ReservationGuest reservationGuest = reservationGuestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReservationGuest not found with ID: " + id));
        reservationGuestRepository.delete(reservationGuest);
    }
}
