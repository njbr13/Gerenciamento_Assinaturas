package com.nilton.gerenciamento_assinatura.controller;


import com.nilton.gerenciamento_assinatura.dto.UserDTO.*;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseLoginDTO;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.service.AutentificacaoService;
import com.nilton.gerenciamento_assinatura.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AutentificacaoService autentificacaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UserResponseDTO> cadastrar(@RequestBody @Valid UserCreateDTO dados){

        User novoUsuario = userService.userCreate(dados);

        UserResponseDTO usuarioCadastrado = new UserResponseDTO(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseLoginDTO> login(@RequestBody @Valid UserLoginDTO dados){

        UserResponseLoginDTO response = autentificacaoService.realizarLogin(dados);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar-perfil")
    public ResponseEntity<User> atualizarPerfil(@RequestBody @Valid UserUptadeDTO dados,
    @AuthenticationPrincipal User usuarioLogado){

        User userUpdate = userService.userUpdate(usuarioLogado.getId(), dados);

        return ResponseEntity.ok(userUpdate);
    }

    @PatchMapping("/trocar-senha")
    public ResponseEntity<Void> trocarSenhaLogago(@RequestBody @Valid UserTrocarSenhaLogadoDTO dados, @AuthenticationPrincipal User usuarioLogado){

        User user = userService.userTrocarSenhaLogado(usuarioLogado.getId(), dados);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<Void> solicitarResetDeSenha(@RequestBody @Valid UserSolicitarResetDTO dados) {

        userService.userSolicitarResetSenha(dados);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid UserRedefinirSenhaDTO dados){
        userService.userEsquecerSenha(dados);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/minha-conta")
    public ResponseEntity<Void> deletarConta(@AuthenticationPrincipal User usuarioLogado) {

        userService.userDelete(usuarioLogado.getId());

        return ResponseEntity.noContent().build();
    }

   
}
