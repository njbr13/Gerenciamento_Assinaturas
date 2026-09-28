package com.nilton.gerenciamento_assinatura.dto.UserDTO.response;

import com.nilton.gerenciamento_assinatura.model.User;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String nome,
        String email,
        java.math.BigDecimal saldoReservado
       )
{

    public UserResponseDTO(User user){
        this(
                user.getNome(),
                user.getEmail(),
                user.getSaldoReservado()
        );
    }


}
