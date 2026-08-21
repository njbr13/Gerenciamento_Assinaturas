package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.UserDTO.UserLoginDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseLoginDTO;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class AutentificacaoService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    public UserResponseLoginDTO realizarLogin( UserLoginDTO loginDTO){

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());

        var authentication = manager.authenticate(authenticationToken);

        var usuarioLogado = (User)authentication.getPrincipal();

        var tokenJWT = tokenService.gerarToken(usuarioLogado);

        return  new UserResponseLoginDTO(usuarioLogado.getNome(), usuarioLogado.getEmail(), tokenJWT);

    }



}
