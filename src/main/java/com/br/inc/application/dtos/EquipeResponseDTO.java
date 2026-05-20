package com.br.inc.application.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para retorno de dados de uma equipe.
 */
public record EquipeResponseDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Esquadrão Backend")
        String nome,
        @Schema(example = "Equipe responsável pelos microserviços de core business")
        String descricao,
        @Schema(example = "[\"João da Silva\", \"Maria Souza\"]")
        List<String> nomesMembros
        ) {

    public static EquipeResponseDTO fromDomain(com.br.inc.domain.entities.Equipe e) {
        return new EquipeResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getMembros().stream().map(com.br.inc.domain.entities.Usuario::getNomeCompleto).toList()
        );
    }
}
