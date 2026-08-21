package com.nilton.gerenciamento_assinatura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import  jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Assinatura {

    @Id // Avisa que este campo é a Chave Primária (ID único)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai somar +1 a cada novo registro (1, 2, 3...)
    @Column(name = "id", unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nomeAssinatura",nullable = false, length = 100) // Não pode ser vazio e tem limite de 100 letras
    @NotBlank(message = "Insira o nome de uma assinatura")
    private String nomeAssinatura; // Ex: "Netflix", "Spotify"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull(message = "A assinatura deve ser vinculada a um usuário")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "assinatura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoricoPagamento> historicos = new ArrayList<>();


    @Column(name = "valor",nullable = false)
    @PositiveOrZero(message = "O valor não pode ser negativo")
    @NotNull(message = "Insira um valor")
    private BigDecimal valor; // Valores em dinheiro sempre usamos BigDecimal por precisão

    @Column(name = "data_vencimento",nullable = false)
    @NotNull(message = "A data de vencimento é obrigatória")
    @FutureOrPresent(message = "A data de vencimento não pode ser no passado")
    private LocalDate dataVencimento; // Guarda ano-mês-dia do vencimento

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 50)
    @NotNull(message = "Insira um categoria. Ex: Streaming, Estudos , etc")
    private CategoriaAssinatura categoriaAssinatura; // Ex: "Streaming", "Trabalho", "Estudos"

    @Column(name = "ativa", nullable = false)
    @Builder.Default
    private Boolean ativa = true; // Por padrão, toda assinatura nova começa como ativa (true)


    /*public Assinatura(User user,String nomeAssinatura, BigDecimal valor, String categoria, LocalDate dataVencimento, Boolean ativa) {
        this.nomeAssinatura = nomeAssinatura;
        this.valor = valor;
        this.categoria = categoria;
        this.dataVencimento = dataVencimento;
        this.ativa = ativa;
        this.user = user;
    }*/
}
