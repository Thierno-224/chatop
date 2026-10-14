package com.chatop.chatop.rental;

import com.chatop.chatop.exception.InvalidCredentialsException;
import com.chatop.chatop.exception.NotFoundException;
import com.chatop.chatop.rental.dto.*;
import com.chatop.chatop.user.UserEntity;
import com.chatop.chatop.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public List<RentalResponseDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::mapRentalToResponse)
                .toList();
    }

    private RentalResponseDTO mapRentalToResponse(RentalEntity rental) {
        return new RentalResponseDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }


    public void createRental(@Valid CreateRentalRequestDTO r) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Aucun utilisateur trouvé"));

        RentalEntity rental = new RentalEntity();

        rental.setName(r.name());
        rental.setSurface(r.surface());
        rental.setPrice(r.price());
        rental.setDescription(r.description());
        rental.setPicture(r.picture());
        rental.setCreatedAt(LocalDateTime.now());
        rental.setOwner(owner);
        rentalRepository.save(rental);
    }

    public RentalResponseDTO getRentalById(Long id) {
        RentalEntity rental =  rentalRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Aucun bien trouvé"));
        return mapRentalToResponse(rental);
    }

    public void updateRental(Long id, @Valid UpdateRentalRequest u) {

        RentalEntity rental =  rentalRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Aucun bien trouvé update"));

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        if (!rental.getOwner().getEmail().equals(email)) {
            throw new InvalidCredentialsException("Vous n'êtes pas propriétaire de ce bien");
        }

        rental.setName(u.name());
        rental.setSurface(u.surface());
        rental.setPrice(u.price());
        rental.setDescription(u.description());
        rental.setUpdatedAt(LocalDateTime.now());

        rentalRepository.save(rental);
    }
}
