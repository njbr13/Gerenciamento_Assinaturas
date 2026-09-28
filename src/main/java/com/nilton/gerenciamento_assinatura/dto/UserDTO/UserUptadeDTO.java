package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import com.nilton.gerenciamento_assinatura.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUptadeDTO(


        String username,

        @Email(message = "É necessário informar um email válido")
        String email,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha,

        java.math.BigDecimal saldoReservado) {

}
