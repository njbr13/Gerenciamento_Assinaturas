package com.nilton.gerenciamento_assinatura.dto.UserDTO.response;

import com.nilton.gerenciamento_assinatura.model.User;

import java.time.LocalDateTime;

public record UserResponseLoginDTO(
        String username,
        String email,
        String token

) {

    public UserResponseLoginDTO(User user, String token){
        this(
                user.getNome(),
                user.getEmail(),
                token

        );
    }
}
