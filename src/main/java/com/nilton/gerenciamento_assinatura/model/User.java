package com.nilton.gerenciamento_assinatura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nilton.gerenciamento_assinatura.enums.Perfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Usuarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // 3. Prepara o equals seguro
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    /*public interface CreateUser{}
    public interface UpdateUser{}*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome_usuario", length = 50, nullable = false)
    @NotBlank(message = "É necessário informar um username")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    @Pattern(regexp = "^(?=(?:.*[\\p{L}]){2}).*$",
            message = "O nome deve conter pelo menos 2 letras (números e caracteres especiais são permitidos)")
    private String nome;

    @Column(name = "email", length = 320, unique = true, nullable = false)
    @NotBlank(message = "É necessário informar um email.")
    @Email(message = "É necessário informar um email válido")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha", length = 255, nullable = false)
    @NotBlank(message = "É necessário informar uma senha")
    private String senha;

    @CreationTimestamp
    @Column(name = "dataCadastro",updatable = false, nullable = false)
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Assinatura> assinaturas = new ArrayList<>();


    @FutureOrPresent(message = "A expiração do token deve ser uma data futura")
    @Column(name = "expiracao_token")
    private LocalDateTime expiracaoToken;

    @Builder.Default
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "token", unique = true, length = 255)
    @Size(max = 255, message = "O token deve ter no máximo 255 caracteres")
    private String resetToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getPassword() {
        return senha; //
    }

    @Override
    public String getUsername() {
        return email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    /*public User(long id, String username, String email, String senha, LocalDateTime dataCadastro) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }*/
}
