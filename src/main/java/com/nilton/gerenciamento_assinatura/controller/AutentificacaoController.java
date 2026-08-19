package com.nilton.gerenciamento_assinatura.controller;

import com.nilton.gerenciamento_assinatura.dto.DadosTokenJWTDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.UserLoginDTO;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutentificacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody@Valid UserLoginDTO loginDTO){

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());

        var authentication = manager.authenticate(authenticationToken);

        var usuarioLogado = (User)authentication.getPrincipal();

        var tokenJWT = tokenService.gerarToken(usuarioLogado);

        return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));

    }



}
