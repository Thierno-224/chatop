package com.chatop.chatop.message;

import com.chatop.chatop.exception.NotFoundException;
import com.chatop.chatop.message.dto.MessageRequestDTO;
import com.chatop.chatop.rental.RentalEntity;
import com.chatop.chatop.rental.RentalRepository;
import com.chatop.chatop.user.UserEntity;
import com.chatop.chatop.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void sendMessage(MessageRequestDTO request) {


        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Aucun utilisateur trouvé"));

        RentalEntity rental = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new NotFoundException("Aucun bien trouvé"));

        MessageEntity message = new MessageEntity();

        message.setUser(sender);
        message.setRental(rental);
        message.setMessage(request.message());

        messageRepository.save(message);
    }
}
