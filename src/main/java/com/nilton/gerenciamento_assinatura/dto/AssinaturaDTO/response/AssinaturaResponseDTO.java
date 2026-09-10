package com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.response;

import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AssinaturaResponseDTO(
        Long id,
        String nomeAssinatura,
        BigDecimal valor,
        LocalDate dataVencimento,
        CategoriaAssinatura categoria
) {
    // Construtor que transforma a Entidade no DTO automaticamente
    public AssinaturaResponseDTO(Assinatura assinatura) {
        this(
                assinatura.getId(),
                assinatura.getNomeAssinatura(),
                assinatura.getValor(),
                assinatura.getDataVencimento(),
                assinatura.getCategoriaAssinatura()
        );
    }
}