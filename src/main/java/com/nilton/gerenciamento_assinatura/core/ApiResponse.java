package com.nilton.gerenciamento_assinatura.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private String mensagem;
    private T dados; // O <T> permite que seja qualquer tipo de objeto (ou nulo)
    private LocalDateTime dataHora;

    // Construtor
    public ApiResponse(String mensagem, T dados) {
        this.mensagem = mensagem;
        this.dados = dados;
        this.dataHora = LocalDateTime.now();
    }

}
