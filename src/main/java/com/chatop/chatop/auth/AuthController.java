package com.chatop.chatop.auth;

import com.chatop.chatop.auth.dto.AuthResponse;
import com.chatop.chatop.auth.dto.LoginRequest;
import com.chatop.chatop.auth.dto.RegisterRequest;
import com.chatop.chatop.user.UserService;
import com.chatop.chatop.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(Authentication authentication) {
        UserResponseDTO user = userService.getMyProfile(authentication.getName());

        return ResponseEntity.ok(user);

    }
}
