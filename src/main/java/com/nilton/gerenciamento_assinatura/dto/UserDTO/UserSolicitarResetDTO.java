package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSolicitarResetDTO(

        @NotBlank(message = "É necessário informar o email.")
        @Email(message = "É necessário informar um email válido. Exemplo: usuario@gmail.com")
        String email) {
}


