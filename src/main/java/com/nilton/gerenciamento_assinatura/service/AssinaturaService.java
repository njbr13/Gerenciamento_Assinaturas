package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.AssinaturaDTO.CreateAssinaturaDTO;
import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssinaturaService {


    private final AssinaturaRepository assinaturaRepository;

    @Transactional
    public Assinatura criarAssinatura(CreateAssinaturaDTO assinaturaDTO, User usuarioLogado){
        Assinatura novaAssinatura = Assinatura.builder()
                .nomeAssinatura(assinaturaDTO.nomeAssinatura())
                .user(usuarioLogado)
                .valor(assinaturaDTO.valor())
                .dataVencimento(assinaturaDTO.dataVencimento())
                .categoriaAssinatura(assinaturaDTO.categoriaAssinatura())
                .build();

       return assinaturaRepository.save(novaAssinatura);
    }


}