package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserReativarContaDTO(

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email
) {
}