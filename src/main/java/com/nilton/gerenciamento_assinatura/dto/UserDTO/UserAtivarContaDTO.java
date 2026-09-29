package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import jakarta.validation.constraints.NotBlank;

public record UserAtivarContaDTO(

    @NotBlank(message = "O token é obrigatório")
    String token
) {
}
