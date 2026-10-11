package com.chatop.chatop.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email obligatoire")
        @Email
        String email,

        @NotBlank(message = "Mot de passe obligatoire")
        String password
) {}
