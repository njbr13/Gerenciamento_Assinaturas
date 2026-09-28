package com.nilton.gerenciamento_assinatura.dto;

import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import java.math.BigDecimal;

public class CategoriaGastoDTO {
    private CategoriaAssinatura categoria;
    private BigDecimal valorTotal;
    private Double porcentagem;

    public CategoriaGastoDTO(CategoriaAssinatura categoria, BigDecimal valorTotal, Double porcentagem) {
        this.categoria = categoria;
        this.valorTotal = valorTotal;
        this.porcentagem = porcentagem;
    }

    // Crie os Getters e Setters (ou adicione as anotações do Lombok: @Getter @Setter @AllArgsConstructor)
    public CategoriaAssinatura getCategoria() { return categoria; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public Double getPorcentagem() { return porcentagem; }
}
