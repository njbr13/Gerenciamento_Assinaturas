package com.nilton.gerenciamento_assinatura.model;

import  jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// O Lombok usa isso para criar os Getters e Setters escondido
@Entity                     // Avisa ao Spring que essa classe vai virar uma tabela no banco
@Table(name = "assinaturas") // Define o nome da tabela no PostgreSQL como "assinaturas"
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Assinatura {

    @Id // Avisa que este campo é a Chave Primária (ID único)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai somar +1 a cada novo registro (1, 2, 3...)
    @Column(name = "id", unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100) // Não pode ser vazio e tem limite de 100 letras
    @NotBlank
    private String nomeAssinatura; // Ex: "Netflix", "Spotify"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "assinatura")
    private List<HistoricoPagamento> historicos = new ArrayList<>();


    @Column(nullable = false)
    private BigDecimal valor; // Valores em dinheiro sempre usamos BigDecimal por precisão

    @Column(nullable = false)
    private LocalDate dataVencimento; // Guarda ano-mês-dia do vencimento

    @Column(nullable = false, length = 100)
    @NotBlank
    private String categoria; // Ex: "Streaming", "Trabalho", "Estudos"

    private Boolean ativa = true; // Por padrão, toda assinatura nova começa como ativa (true)

    @Builder
    public Assinatura(String nomeAssinatura, BigDecimal valor, String categoria, LocalDate dataVencimento, Boolean ativa) {
        this.nomeAssinatura = nomeAssinatura;
        this.valor = valor;
        this.categoria = categoria;
        this.dataVencimento = dataVencimento;
        this.ativa = ativa;
    }
}
