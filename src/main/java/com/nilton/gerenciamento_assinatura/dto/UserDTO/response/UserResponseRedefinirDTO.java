package com.nilton.gerenciamento_assinatura.dto.UserDTO.response;

public record UserResponseRedefinirDTO(

        String confirmacao) {

    public UserResponseRedefinirDTO(String confirmacao){
        this.confirmacao = confirmacao;
    }

}

