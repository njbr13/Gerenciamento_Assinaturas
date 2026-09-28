package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.CreateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.UpdateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;

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

    

}