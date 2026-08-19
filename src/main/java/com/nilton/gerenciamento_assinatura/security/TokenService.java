package com.nilton.gerenciamento_assinatura.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nilton.gerenciamento_assinatura.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET:minha-senha-super-secreta-de-desenvolvimento}")
    private String secret;

    private static final String ISSUER = "Gerenciamento_Assinaturas";

    public String gerarToken(User user){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id: ", user.getId())
                    .withClaim("usename: ", user.getUsername())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);

        }catch (JWTCreationException JWT){
            throw new RuntimeException("Erro ao gerar token: ", JWT);
        }
    }

    public String getSubject(String tokenJWT){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException JWT) {
            throw new RuntimeException("Token JWT inválido ou expirado!", JWT);
        }
    }


    private Instant gerarDataExpiracao() {
        // Define que o token expira em 2 horas a partir do momento da criação.
        // O ZoneOffset.of("-03:00") ajusta para o horário de Brasília.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
