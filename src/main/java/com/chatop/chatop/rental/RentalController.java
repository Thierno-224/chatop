package com.chatop.chatop.rental;

import com.chatop.chatop.rental.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Locations",
        description = "Gestion des biens immobiliers"
)
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Operation(
            summary = "Récupérer tous les biens",
            description = "Permet à un utilisateur authentifié de consulter la liste des biens immobiliers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des biens récupérée avec succès"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<RentalListResponseDTO> getAllRentals() {

        List<RentalResponseDTO> rentals = rentalService.getAllRentals();

        return ResponseEntity.ok(new RentalListResponseDTO(rentals));
    }

    @Operation(
            summary = "Récupérer un bien par son identifiant",
            description = "Permet à un utilisateur authentifié de consulter les détails d'un bien immobilier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bien récupéré avec succès"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aucun bien trouvé avec cet identifiant",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @Operation(
            summary = "Créer un bien immobilier",
            description = "Permet à un utilisateur authentifié de créer un bien immobilier avec une image."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bien créé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides ou image non conforme",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> createRental(
            @Valid @ModelAttribute CreateRentalRequestDTO r
    ) {
        rentalService.createRental(r);

        return ResponseEntity.ok(
                new MessageResponseDTO("Rental created !")
        );
    }

    @Operation(
            summary = "Modifier un bien immobilier",
            description = "Permet à un utilisateur authentifié de modifier les informations d'un bien immobilier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bien modifié avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données de modification invalides",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bien introuvable",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateRental(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRentalRequest u
    ) {
        rentalService.updateRental(id, u);

        return ResponseEntity.ok(
                new MessageResponseDTO("Renatl update !")
        );
    }
}
