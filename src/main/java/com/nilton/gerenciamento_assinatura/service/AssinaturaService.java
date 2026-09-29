package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.CategoriaGastoDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.CreateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.UpdateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.enums.CategoriaAssinatura;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final com.nilton.gerenciamento_assinatura.repository.UserRepository userRepository;

    @Transactional
    public Assinatura criarAssinatura(CreateAssinaturaDTO assinaturaDTO, User usuarioLogado) {
        Assinatura novaAssinatura = Assinatura.builder()
                .nomeAssinatura(assinaturaDTO.nomeAssinatura())
                .user(usuarioLogado)
                .valor(assinaturaDTO.valor())
                .dataVencimento(assinaturaDTO.dataVencimento())
                .categoriaAssinatura(assinaturaDTO.categoriaAssinatura())
                .build();
        return assinaturaRepository.save(novaAssinatura);
    }

    @Transactional
    public Assinatura atualizarAssinatura(Long id, UpdateAssinaturaDTO assinaturaDTO, User usuarioLogado) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada!"));

        // Validação de IDOR (Garante que a assinatura pertence ao usuário logado)
        if (!assinatura.getUser().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Você não tem permissão para alterar esta assinatura.");
        }

        if (assinaturaDTO.nomeAssinatura() != null && !assinaturaDTO.nomeAssinatura().isBlank()) {
            
            Optional<Assinatura> existente = assinaturaRepository.findByNomeAssinaturaAndUserId(assinaturaDTO.nomeAssinatura(), usuarioLogado.getId());
            if (existente.isPresent() && !existente.get().getId().equals(id)) {
                throw new RuntimeException("Você já possui uma assinatura com este nome.");
            }
            
            assinatura.setNomeAssinatura(assinaturaDTO.nomeAssinatura());
        }

        if (assinaturaDTO.valor() != null) {
            assinatura.setValor(assinaturaDTO.valor());
        }

        if (assinaturaDTO.dataVencimento() != null) {
            assinatura.setDataVencimento(assinaturaDTO.dataVencimento());
        }

        if (assinaturaDTO.categoriaAssinatura() != null) {
            assinatura.setCategoriaAssinatura(assinaturaDTO.categoriaAssinatura());
        }

        if (assinaturaDTO.ativo() != null) {
            assinatura.setAtiva(assinaturaDTO.ativo());
        }

        return assinaturaRepository.save(assinatura);
    }

    @Transactional 
    public List<Assinatura> listarAssinatura(User usuarioLogado){

        return assinaturaRepository.findByUser_id(usuarioLogado.getId());
    }

    @Transactional
    public void deletarAssinatura(Long id, User usuarioLogado){

        Assinatura assinaturaDeletada = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada!"));

        if(!assinaturaDeletada.getUser().getId().equals(usuarioLogado.getId())){
            throw new RuntimeException("Você não tem permissão para deletar esta assinatura.");
        }

        assinaturaDeletada.setAtiva(false);
        assinaturaRepository.save(assinaturaDeletada);


    }

    @Transactional
    public List<CategoriaGastoDTO> obterGastosPorCategoria(User usuarioLogado) {
        List<Assinatura> assinaturas = assinaturaRepository.findByUser_id(usuarioLogado.getId());
        
        Map<CategoriaAssinatura, BigDecimal> somaPorCategoria = new HashMap<>();
        BigDecimal totalGeral = BigDecimal.ZERO;

       
        for (Assinatura assinatura : assinaturas) {
            if (assinatura.getAtiva()) { 
                CategoriaAssinatura cat = assinatura.getCategoriaAssinatura();
                BigDecimal valor = assinatura.getValor();
                
                
                BigDecimal valorAtual = somaPorCategoria.getOrDefault(cat, BigDecimal.ZERO);
                somaPorCategoria.put(cat, valorAtual.add(valor));
                
              
                totalGeral = totalGeral.add(valor);
            }
        }

       
        List<CategoriaGastoDTO> resultado = new ArrayList<>();
        
        for (Map.Entry<CategoriaAssinatura, BigDecimal> entry : somaPorCategoria.entrySet()) {
            CategoriaAssinatura categoria = entry.getKey();
            BigDecimal totalCategoria = entry.getValue();
            
            Double porcentagem = 0.0;
           
            if (totalGeral.compareTo(BigDecimal.ZERO) > 0) {
                porcentagem = (totalCategoria.doubleValue() / totalGeral.doubleValue()) * 100.0;
                porcentagem = Math.round(porcentagem * 100.0) / 100.0; 
            }
            
            resultado.add(new CategoriaGastoDTO(categoria, totalCategoria, porcentagem));
        }

      
        Collections.sort(resultado, new Comparator<CategoriaGastoDTO>() {
            @Override
            public int compare(CategoriaGastoDTO c1, CategoriaGastoDTO c2) {
              
                return c2.getValorTotal().compareTo(c1.getValorTotal()); 
            }
        });

        return resultado;
    }

    @Transactional
    public BigDecimal somarGastoTotais(User usuarioLogado){

        List<Assinatura> assinaturas = assinaturaRepository.findByUser_id(usuarioLogado.getId());

        BigDecimal totalGeral = BigDecimal.ZERO;

        for(Assinatura assinatura : assinaturas){
            if(assinatura.getAtiva()){
                totalGeral = totalGeral.add(assinatura.getValor());
            }
        }
        return totalGeral;
    }

    @Transactional
    public BigDecimal obterSaldoDisponivel(User usuarioLogado) {
        User user = userRepository.findById(usuarioLogado.getId()).orElse(usuarioLogado);
        BigDecimal saldoCarteira = user.getSaldoReservado() != null ? user.getSaldoReservado() : BigDecimal.ZERO;
        BigDecimal saldoReservado = somarGastoTotais(usuarioLogado);
        
        return saldoCarteira.subtract(saldoReservado);
    }

    public Assinatura reativarAssinatura(Long id , User usuarioLogado){

        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Assinatura não encontrada!"));

        if(!assinatura.getUser().getId().equals(usuarioLogado.getId())){
            throw new RuntimeException("Você não tem permissão para reativar esta assinatura.");
        }

        if(!assinatura.getAtiva()){
            assinatura.setAtiva(true);
        }
        else {
            throw new RuntimeException("A assinatura já está ativa.");
        }

        return assinaturaRepository.save(assinatura);
    }


}