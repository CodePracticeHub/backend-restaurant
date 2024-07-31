package com.restaurantmanagement.common;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformation {

    @NotBlank
    @Size(min = 13, max = 19)
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be between 13 and 19 digits")
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

    public boolean isCardExpired(String cardExpiryDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate expiryDate = LocalDate.parse("01/" + cardExpiryDate, formatter);
            return expiryDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid expiration date format. Please use MM/YY.");
        }
    }
}
