package com.chatop.chatop.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalResponseDTO(
        Long id,
        String name,
        BigDecimal surface,
        BigDecimal price,
        String picture,
        String description,

        @JsonProperty("owner_id")
        Long ownerId,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy/MM/dd")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy/MM/dd")
        LocalDateTime updatedAt
) {
}