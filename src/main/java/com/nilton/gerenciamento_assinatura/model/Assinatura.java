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


@Entity                     
@Table(name = "assinaturas")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Assinatura {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nomeAssinatura",nullable = false, length = 100) 
    @NotBlank(message = "Insira o nome de uma assinatura")
    private String nomeAssinatura;

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
    private BigDecimal valor;   

    @Column(name = "data_vencimento",nullable = false)
    @NotNull(message = "A data de vencimento é obrigatória")
    @FutureOrPresent(message = "A data de vencimento não pode ser no passado")
    private LocalDate dataVencimento; 
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 50)
    @NotNull(message = "Insira um categoria. Ex: Streaming, Estudos , etc")
    private CategoriaAssinatura categoriaAssinatura;

    @Column(name = "ativa", nullable = false)
    @Builder.Default
    private Boolean ativa = true;


    /*public Assinatura(User user,String nomeAssinatura, BigDecimal valor, String categoria, LocalDate dataVencimento, Boolean ativa) {
        this.nomeAssinatura = nomeAssinatura;
        this.valor = valor;
        this.categoria = categoria;
        this.dataVencimento = dataVencimento;
        this.ativa = ativa;
        this.user = user;
    }*/
}
