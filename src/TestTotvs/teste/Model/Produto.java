package Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Produto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Insira o nome do produto")
    private String nome;

    @Column(name = "preco")
    @NotNull(message = "Insira o preco do produto")
    @PositiveOrZero(message = "O preco nao pode ser negativo")
    private BigDecimal preco;

    @Column(name = "quantidade_estoque")
    @NotNull(message = "Insira a quantidade do estoque do produto")
    @PositiveOrZero(message = "A quantidade do estoque do produto nao pode ser negativa")
    private int quantidadeEstoque;

    @JoinColumn(name = "categoria_id", nullable = false)
    @ManyToOne
    @NotNull(message = "Insira a categoria")
    private Categoria categoria;
}
