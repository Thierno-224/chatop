package com.chatop.chatop.rental;

import com.chatop.chatop.rental.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalListResponseDTO> getAllRentals() {

        List<RentalResponseDTO> rentals = rentalService.getAllRentals();

        return ResponseEntity.ok(new RentalListResponseDTO(rentals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Long id) {
        return  ResponseEntity.ok(rentalService.getRentalById(id));
    }


    @PostMapping
    public ResponseEntity<MessageResponseDTO> createRental(@Valid @RequestBody
                                                           CreateRentalRequestDTO r) {
        rentalService.createRental(r);
        return ResponseEntity.ok(new MessageResponseDTO("Rental created !"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateRental(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateRentalRequest u) {
        rentalService.updateRental(id, u);
        return ResponseEntity.ok(new MessageResponseDTO("Renatl update !"));
    }




}
