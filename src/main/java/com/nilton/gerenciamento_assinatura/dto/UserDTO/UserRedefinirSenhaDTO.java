package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import com.nilton.gerenciamento_assinatura.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRedefinirSenhaDTO(
        @NotBlank(message = "O token não pode ser vazio")
        String token,

        @NotBlank( message = "É necessário informar uma senha")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial"
        )
        String novaSenha) {
}
