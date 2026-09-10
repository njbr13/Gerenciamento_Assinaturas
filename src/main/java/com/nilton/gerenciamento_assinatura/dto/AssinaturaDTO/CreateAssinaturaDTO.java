package com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO;

import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateAssinaturaDTO(

        @NotBlank(message = "O nome da assinatura não pode ser vazio")
        @Size(min = 2, max = 50)
        String nomeAssinatura,

        @NotNull(message = "É obrigatório inserir um valor")
        @PositiveOrZero(message = "O valor não pode ser negativo")
        BigDecimal valor,

        @NotNull(message = "A data de vencimento é obrigatória")
        @FutureOrPresent(message = "A data de vencimento não pode ser no passado")
        LocalDate dataVencimento,

        @NotNull(message = "Insira um categoria. Ex: Streaming, Estudos , etc")
        CategoriaAssinatura categoriaAssinatura

        ) {
}
