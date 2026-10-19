
package com.chatop.chatop.auth;

import com.chatop.chatop.auth.dto.AuthResponse;
import com.chatop.chatop.auth.dto.LoginRequest;
import com.chatop.chatop.auth.dto.RegisterRequest;
import com.chatop.chatop.user.UserService;
import com.chatop.chatop.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Authentification",
        description = "Gestion de l'inscription, de la connexion et du profil utilisateur"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(
            summary = "Inscrire un utilisateur",
            description = "Permet de créer un compte utilisateur et de recevoir un token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Compte utilisateur créé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données d'inscription invalides",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Adresse e-mail déjà utilisée",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @Operation(
            summary = "Connecter un utilisateur",
            description = "Authentifie un utilisateur à partir de ses identifiants et retourne un token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentification réussie"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données de connexion invalides",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Identifiants incorrects",
                    content = @Content
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Récupérer le profil de l'utilisateur connecté",
            description = "Retourne les informations de l'utilisateur authentifié à partir de son token JWT."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profil utilisateur récupéré avec succès"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié ou token JWT invalide",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur introuvable",
                    content = @Content
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(
            Authentication authentication
    ) {
        UserResponseDTO user = userService.getMyProfile(
                authentication.getName()
        );

        return ResponseEntity.ok(user);
    }
}
