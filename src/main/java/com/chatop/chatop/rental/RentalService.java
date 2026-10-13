package com.chatop.chatop.rental;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
}
