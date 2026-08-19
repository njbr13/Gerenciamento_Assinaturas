package com.nilton.gerenciamento_assinatura.model;

import com.nilton.gerenciamento_assinatura.enums.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "historico_pagamentos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "valor_pago", nullable = false)
    @NotNull(message = "O valor pago é obrigatório")
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private BigDecimal valorPago;

    @Column(name = "data_pagamento", nullable = false)
    @NotNull(message = "Obrigatório a data de pagamento")
    @PastOrPresent(message = "A data de pagamento não pode ser no futuro")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusPagamento", nullable = false, length = 30)
    @NotNull(message = "O status do pagamento é obrigatório")
    private StatusPagamento statusPagamento;

    @ManyToOne
    @JoinColumn(name = "assinatura_id", nullable = false)
    @NotNull(message = "O histórico deve estar vinculado a uma assinatura")
    private Assinatura assinatura;


    /*public HistoricoPagamento(BigDecimal valorPago, LocalDate dataPagamento, String status, Assinatura assinatura) {
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.assinatura = assinatura;
    }*/

}
