package com.chatop.chatop.auth;


import com.chatop.chatop.auth.dto.AuthResponse;
import com.chatop.chatop.auth.dto.LoginRequest;
import com.chatop.chatop.auth.dto.RegisterRequest;
import com.chatop.chatop.exception.EmailAlreadyExistsException;
import com.chatop.chatop.exception.InvalidCredentialsException;
import com.chatop.chatop.security.JwtService;
import com.chatop.chatop.user.UserEntity;
import com.chatop.chatop.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Essayer une autre adresse mail");
        }

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        UserEntity savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new InvalidCredentialsException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }


}
