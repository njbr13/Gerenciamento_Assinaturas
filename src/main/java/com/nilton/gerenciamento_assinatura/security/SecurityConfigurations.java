package com.nilton.gerenciamento_assinatura.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // O Spring pega este objeto que retornamos e guarda na "Caixa de Ferramentas"
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // O Spring executa isso, pega o Manager pronto e deixa disponível para o seu Controller usar.
        return configuration.getAuthenticationManager();
    }

    /*
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desabilita proteção de formulário web (nós usamos API REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Libera todas as rotas temporariamente
                );
        return http.build();
    }*/



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable()) // Desabilita proteção de formulário web (nós usamos API REST)

            // 1. Dizemos ao Spring: "Não guarde sessão do usuário, nossa API é STATELESS (usa JWT)"
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 2. Aqui começam as regras de bloqueio e liberação de rotas
            .authorizeHttpRequests(auth -> auth

                    // ROTAS PÚBLICAS: Não exigem Token JWT
                    .requestMatchers(HttpMethod.POST, "/usuarios/cadastrar").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios/esqueci-minha-senha").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios/redefinir-senha").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/estoque-critico").permitAll()

                    // (Opcional) Se você tiver uma rota só para ADMIN, configuraria assim:
                    // .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")

                    // REGRA GERAL: Qualquer outra requisição que não foi listada acima EXIGE que o usuário esteja autenticado
                    .anyRequest().authenticated()
            )

            // 3. Avisamos ao Spring para colocar o NOSSO filtro (que lê o JWT) ANTES do filtro padrão dele
            // Obs: Lembre-se de injetar (@Autowired) o seu 'SecurityFilter' na classe para esta linha funcionar
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

            .build();
}

}
