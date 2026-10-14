package com.chatop.chatop.rental.dto;

import java.util.List;

public record RentalListResponseDTO(
        List<RentalResponseDTO> rentals
) {}
