package com.chatop.chatop.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageRequestDTO(
        @NotBlank(message = "Le message est obligatoire")
        String message,

        @JsonProperty("rental_id")
        @NotNull(message = "L'identifiant du bien est obligatoire")
        Long rentalId
) {
}