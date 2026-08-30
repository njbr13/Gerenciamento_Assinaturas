package com.nilton.gerenciamento_assinatura.controller;

import com.nilton.gerenciamento_assinatura.core.ApiResponse;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.*;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseLoginDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseRedefinirDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.response.UserResponseResetDTO;
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
    public ResponseEntity<ApiResponse> cadastrar(@RequestBody @Valid UserCreateDTO dados) {

        User novoUsuario = userService.userCreate(dados);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Usuario criado com sucesso!", dados));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseLoginDTO> login(@RequestBody @Valid UserLoginDTO dados) {

        UserResponseLoginDTO response = autentificacaoService.realizarLogin(dados);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar-perfil")
    public ResponseEntity<ApiResponse> atualizarPerfil(@RequestBody @Valid UserUptadeDTO dados,
            @AuthenticationPrincipal User usuarioLogado) {

        System.out.println("EMAIL QUE CHEGOU: " + dados.email()); // Adicione isso!
        System.out.println("ID LOGADO: " + usuarioLogado.getId());

        User userUpdate = userService.userUpdate(usuarioLogado.getId(), dados);

        UserResponseDTO resposta = new UserResponseDTO(userUpdate);

        return ResponseEntity.ok(new ApiResponse<>("Usuário atualizado com sucesso", resposta));
    }

    @PatchMapping("/trocar-senha")
    public ResponseEntity<ApiResponse> trocarSenhaLogago(@RequestBody @Valid UserTrocarSenhaLogadoDTO dados,
            @AuthenticationPrincipal User usuarioLogado) {

        User user = userService.userTrocarSenhaLogado(usuarioLogado.getId(), dados);

        return ResponseEntity.ok(new ApiResponse<>("Senha trocada com sucesso", null));
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<ApiResponse> solicitarResetDeSenha(@RequestBody @Valid UserSolicitarResetDTO dados) {

        userService.userSolicitarResetSenha(dados);

        String confirmacao = "Token enviado ao email:" + dados.email()
                + ". Não esqueça de verificar a caixa de SPAM";

        return ResponseEntity.ok(new ApiResponse<>(confirmacao, null));
        // return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<ApiResponse> redefinirSenha(@RequestBody @Valid UserRedefinirSenhaDTO dados) {
        userService.userEsquecerSenha(dados);

        String confirmacao = "Senha atualizada com Sucesso. Não se esqueça";
        return ResponseEntity.ok(new ApiResponse(confirmacao, null));
    }

    @DeleteMapping("/minha-conta")
    public ResponseEntity<Void> deletarConta(@AuthenticationPrincipal User usuarioLogado) {

        userService.userDelete(usuarioLogado.getId());

        return ResponseEntity.noContent().build();
    }

}
