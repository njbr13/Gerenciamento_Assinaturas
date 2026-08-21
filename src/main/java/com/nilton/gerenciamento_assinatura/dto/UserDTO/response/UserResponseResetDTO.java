package com.nilton.gerenciamento_assinatura.dto.UserDTO.response;

public record UserResponseResetDTO(

        String confirmacao) {


    public UserResponseResetDTO(String confirmacao) {
       this.confirmacao = confirmacao;
    }
}
