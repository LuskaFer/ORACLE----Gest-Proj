package com.br.inc.application.dtos;

import java.util.List;

/**
 * DTO para retorno de dados de uma equipe.
 */
public record EquipeResponseDTO(
        Long id,
        String nome,
        String descricao,
        List<String> nomesMembros
        ) {

    public static EquipeResponseDTO fromEntity(com.br.inc.domain.entities.Equipe e) {
        return new EquipeResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getMembros().stream().map(com.br.inc.domain.entities.Usuario::getNomeCompleto).toList()
        );
    }
}
