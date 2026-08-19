package com.nilton.gerenciamento_assinatura.dto.UserDTO;

import com.nilton.gerenciamento_assinatura.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(

        @NotBlank(message = "É necessário informar um username")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        @Pattern(regexp = "^(?=(?:.*[\\p{L}]){2}).*$",
                message = "O nome deve conter pelo menos 2 letras (números e caracteres especiais são permitidos)")
        String username,

        @NotBlank( message = "É necessário informar um email.")
        @Email(message = "É necessário informar um email válido")
        String email,

        @NotBlank( message = "É necessário informar uma senha")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha) {

}
