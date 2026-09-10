package com.nilton.gerenciamento_assinatura.enums;

import java.util.Arrays;

public enum CategoriaAssinatura {

    STREAMING,
    EDUCACAO,
    TRABALHO,
    SAUDE,
    GAMES,
    OUTROS;

    public static CategoriaAssinatura validacaoEnum(String valor){

        if(valor == null || valor.isBlank()){
            return null;
        }

        for (CategoriaAssinatura categoria : CategoriaAssinatura.values()) {
            if (categoria.name().equalsIgnoreCase(valor.trim())) {
                return categoria; // Aceita "streaming", "Streaming" ou "STREAMING"
            }
        }

        throw new IllegalArgumentException(
                "Categoria '" + valor + "' é inválida. Categorias aceitas: " + Arrays.toString(CategoriaAssinatura.values())
        );
    }

}
