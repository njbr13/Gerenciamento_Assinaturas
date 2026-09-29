package com.nilton.gerenciamento_assinatura.controller;

import com.nilton.gerenciamento_assinatura.core.ApiResponse;
import com.nilton.gerenciamento_assinatura.dto.CategoriaGastoDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.CreateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.UpdateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.response.AssinaturaResponseDTO;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.service.AssinaturaService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    @Autowired
    AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<ApiResponse<AssinaturaResponseDTO>> cadastrarAssinatura(
            @RequestBody @Valid CreateAssinaturaDTO assinaturaDTO, @AuthenticationPrincipal User usuarioLogado) {

        Assinatura novaAssinatura = assinaturaService.criarAssinatura(assinaturaDTO, usuarioLogado);

        AssinaturaResponseDTO assinaturaResponseDTO = new AssinaturaResponseDTO(novaAssinatura);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Assinatura criada com sucesso", assinaturaResponseDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AssinaturaResponseDTO>> atualizarAssinatura(@PathVariable Long id,
            @RequestBody @Valid UpdateAssinaturaDTO assinaturaDTO, @AuthenticationPrincipal User usuarioLogado) {

        Assinatura assinaturaAtualizada = assinaturaService.atualizarAssinatura(id, assinaturaDTO, usuarioLogado);

        AssinaturaResponseDTO assinaturaResponseDTO = new AssinaturaResponseDTO(assinaturaAtualizada);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Assinatura atualizada com sucesso", assinaturaResponseDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<AssinaturaResponseDTO>>> listarAssinatura(@AuthenticationPrincipal User usuarioLogado){

        List<Assinatura> assinaturas = assinaturaService.listarAssinatura(usuarioLogado);

        List<AssinaturaResponseDTO> assinaturasDTO = new ArrayList<>();

        for (Assinatura assinatura : assinaturas) {

             AssinaturaResponseDTO assinaturaDTO = new AssinaturaResponseDTO(assinatura); 

              assinaturasDTO.add(assinaturaDTO);
            
        }
        return ResponseEntity.ok(new ApiResponse<>("Assinaturas listadas com sucesso!", assinaturasDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deletarAssinatura(@PathVariable Long id, @AuthenticationPrincipal User usuarioLogado){

        assinaturaService.deletarAssinatura(id, usuarioLogado);

        return ResponseEntity.ok(new ApiResponse<>("Assinatura deletada com sucesso", null));
    }

    @GetMapping("/gastos-por-categoria")
    public ResponseEntity<ApiResponse<List<CategoriaGastoDTO>>>listarGastosPorCategoria(@AuthenticationPrincipal User usuarioLogado) {
        
        List<CategoriaGastoDTO> gastos = assinaturaService.obterGastosPorCategoria(usuarioLogado);
        
        return ResponseEntity.ok(new ApiResponse<>("Gastos calculados com sucesso!", gastos));
    }

    @GetMapping("/gasto-total")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> obterGastoTotal(@AuthenticationPrincipal User usuarioLogado) {
        java.math.BigDecimal gastoTotal = assinaturaService.somarGastoTotais(usuarioLogado);
        return ResponseEntity.ok(new ApiResponse<>("Gasto total calculado com sucesso!", gastoTotal));
    }

    @GetMapping({"/saldo-disponivel", "/gasto-disponivel"})
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> obterSaldoDisponivel(@AuthenticationPrincipal User usuarioLogado) {
        java.math.BigDecimal saldoDisponivel = assinaturaService.obterSaldoDisponivel(usuarioLogado);
        return ResponseEntity.ok(new ApiResponse<>("Saldo disponível calculado com sucesso!", saldoDisponivel));
    }
    
    
    @PatchMapping("/reativar/{id}")
    public ResponseEntity<ApiResponse<AssinaturaResponseDTO>> reativarAssinatura(@PathVariable Long id, @AuthenticationPrincipal User usuarioLogado) {
        Assinatura assinaturaReativada = assinaturaService.reativarAssinatura(id, usuarioLogado);
        AssinaturaResponseDTO assinaturaResponseDTO = new AssinaturaResponseDTO(assinaturaReativada);
        return ResponseEntity.ok(new ApiResponse<>("Assinatura reativada com sucesso!", assinaturaResponseDTO));
    }

}
