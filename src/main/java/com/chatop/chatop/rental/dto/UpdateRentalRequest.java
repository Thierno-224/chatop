package com.chatop.chatop.rental.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateRentalRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 255)
        String name,

        @NotNull(message = "La surface est obligatoire")
        @DecimalMin(value = "0.01", message = "La surface doit être positive")
        BigDecimal surface,

        @NotNull(message = "Le prix est obligatoire")
        @DecimalMin(value = "0.01", message = "Le prix doit être positif")
        BigDecimal price,

        String picture,

        @NotBlank(message = "La description est obligatoire")
        @Size(max = 2000, message = "La description ne doit pas dépasser 2000 caractères")
        String description
) {
}
