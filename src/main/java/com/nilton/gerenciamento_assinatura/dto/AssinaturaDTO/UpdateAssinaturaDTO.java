package com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO;

import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateAssinaturaDTO(

        String nomeAssinatura,

        @PositiveOrZero(message = "O valor não pode ser negativo") BigDecimal valor,

        @FutureOrPresent(message = "A data não pode ser no passado") LocalDate dataVencimento,

        CategoriaAssinatura categoriaAssinatura,

        Boolean ativo) {
}
