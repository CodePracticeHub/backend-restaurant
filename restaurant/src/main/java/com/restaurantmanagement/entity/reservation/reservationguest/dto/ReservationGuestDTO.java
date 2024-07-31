package com.restaurantmanagement.entity.reservation.reservationguest.dto;

import com.restaurantmanagement.common.PaymentInformation;
import com.restaurantmanagement.entity.reservation.reservationguest.ReservationGuest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationGuestDTO {

    private Long reservationGuestId;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String contactNumber;

    private PaymentInformation paymentInformation;

    public ReservationGuestDTO(ReservationGuest reservationGuest) {
        this.reservationGuestId = reservationGuest.getReservationGuestId();
        this.name = reservationGuest.getName();
        this.email = reservationGuest.getEmail();
        this.contactNumber = reservationGuest.getContactNumber();
        this.paymentInformation = new PaymentInformation(
                reservationGuest.getCardNumber(),
                reservationGuest.getCardHolderName(),
                reservationGuest.getCardExpiryDate(),
                reservationGuest.getCardCVV()
        );
    }
}
