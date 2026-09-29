package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import com.nilton.gerenciamento_assinatura.model.User;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record UserUptadeDTO(


        String username,

        @Email(message = "É necessário informar um email válido")
        String email,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha,

        @PositiveOrZero(message = "O saldo reservado não pode ser negativo")
        @Digits(integer = 10, fraction = 2, message = "O saldo deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
        BigDecimal saldoReservado) {

}
