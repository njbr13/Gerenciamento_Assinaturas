package com.nilton.gerenciamento_assinatura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Usuarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // 3. Prepara o equals seguro
@NoArgsConstructor
public class User {

    public interface CreateUser{}
    public interface UpdateUser{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome_usuario", length = 10, nullable = false)
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    private String username;

    @Column(name = "email", length = 320, unique = true, nullable = false)
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha", length = 16, nullable = false, unique = true)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(min = 6, max = 16)
    private String senha;

    private LocalDateTime dataCadastro = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<Assinatura> assinatura = new ArrayList<>();

    private boolean ativo = true;


    public User(long id, String username, String email, String senha, LocalDateTime dataCadastro) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }
}
