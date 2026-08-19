package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(

        @NotBlank(message = "É necessário informar o email.")
        String email,

        @NotBlank(message = "É necessário informar uma senha.")
        String senha) {
}
