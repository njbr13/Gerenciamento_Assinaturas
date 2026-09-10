package com.nilton.gerenciamento_assinatura.controller;

import com.nilton.gerenciamento_assinatura.core.ApiResponse;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.CreateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.response.AssinaturaResponseDTO;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.service.AssinaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    @Autowired
    AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<ApiResponse<AssinaturaResponseDTO>> cadastrarAssinatura(@RequestBody  @Valid CreateAssinaturaDTO assinaturaDTO, @AuthenticationPrincipal User usuarioLogado){

        Assinatura novaAssinatura = assinaturaService.criarAssinatura(assinaturaDTO, usuarioLogado);

        AssinaturaResponseDTO assinaturaResponseDTO = new AssinaturaResponseDTO(novaAssinatura);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Assinatura criada com sucesso", assinaturaResponseDTO));
    }
}
