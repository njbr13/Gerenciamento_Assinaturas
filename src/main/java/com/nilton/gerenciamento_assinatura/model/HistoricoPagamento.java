package com.nilton.gerenciamento_assinatura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class HistoricoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "valor_pago", nullable = false)
    @PositiveOrZero
    private BigDecimal valorPago;

    @Column(name = "data_pagamento", nullable = false)
    @PastOrPresent
    private LocalDate dataPagamento;

    @Column(name = "status", nullable = false)
    @NotBlank
    private String status;

    @ManyToOne
    @JoinColumn(name = "assinatura_id", nullable = false)
    private Assinatura assinatura;


    public HistoricoPagamento(BigDecimal valorPago, LocalDate dataPagamento, String status) {
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }


    /*private BigDecimal valorAnterior;*/
}
