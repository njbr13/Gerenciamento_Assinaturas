package com.nilton.gerenciamento_assinatura.dto.UserDTO.response;

import com.nilton.gerenciamento_assinatura.model.User;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String nome,
        String email ,
        LocalDateTime dataCadastro,
        boolean ativo)
{

    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getDataCadastro(),
                user.isAtivo()
        );
    }


}
