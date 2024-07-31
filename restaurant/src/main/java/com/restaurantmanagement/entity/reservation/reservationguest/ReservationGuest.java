package com.restaurantmanagement.entity.reservation.reservationguest;

import com.restaurantmanagement.common.PaymentInformation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ReservationGuest")
public class ReservationGuest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_reservation")
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

    @NotBlank
    @Size(min = 13, max = 19)
    private String cardNumber;

    @NotBlank
    @Size(max = 50)
    private String cardHolderName;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "Card expiry date must be in MM/YY format")
    private String cardExpiryDate;

    @NotBlank
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cardCVV;
}
