package com.chatop.chatop.user;

import com.chatop.chatop.exception.NotFoundException;
import com.chatop.chatop.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserResponseDTO getMyProfile(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Aucun utilisateur trouvé"));
        return mapUserToResponse(user);
    }

    private UserResponseDTO mapUserToResponse(UserEntity user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Aucun utilisateur trouvé"));
        return mapUserToResponse(user);
    }
}
