package com.nilton.gerenciamento_assinatura.security;

import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        var token = recuperarToken(request);

        if(token != null){

            var emailUsuario = tokenService.getSubject(token);

            var usuarioOptional = userRepository.findByEmail(emailUsuario);

            if(usuarioOptional.isPresent()){
                var usuario = usuarioOptional.get();

                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token =  authorizationHeader.replace("Bearer ", "").trim();
            return token.isEmpty() ? null : token;
        }
        return null;
    }
}
