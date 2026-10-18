package com.chatop.chatop.message;

import com.chatop.chatop.message.dto.MessageRequestDTO;
import com.chatop.chatop.rental.dto.MessageResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<MessageResponseDTO> sendMessage(@Valid @RequestBody MessageRequestDTO requestDTO) {
        System.out.println("DTO reçu : " + requestDTO);
        messageService.sendMessage(requestDTO);
        return ResponseEntity.ok(new MessageResponseDTO("Message send with success"));
    }

    @GetMapping("/test-error")
    public void testError() {
        throw new RuntimeException("Erreur de test");
    }
}

