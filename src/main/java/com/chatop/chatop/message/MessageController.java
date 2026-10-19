
package com.chatop.chatop.message;

import com.chatop.chatop.message.dto.MessageRequestDTO;
import com.chatop.chatop.rental.dto.MessageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Messages",
        description = "Gestion des messages liés aux biens immobiliers"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(
            summary = "Envoyer un message",
            description = "Permet à un utilisateur authentifié d'envoyer un message concernant un bien immobilier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message envoyé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données du message invalides",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bien immobilier ou utilisateur introuvable",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(
            @Valid @RequestBody MessageRequestDTO requestDTO
    ) {
        messageService.sendMessage(requestDTO);

        return ResponseEntity.ok(
                new MessageResponseDTO("Message send with success")
        );
    }
}
