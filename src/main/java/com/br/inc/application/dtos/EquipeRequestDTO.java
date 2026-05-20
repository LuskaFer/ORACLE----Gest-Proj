package com.br.inc.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para entrada de dados de equipe.
 */
public record EquipeRequestDTO(
        @Schema(example = "Esquadrão Backend")
        String nome,
        @Schema(example = "Equipe responsável pelos microserviços de core business")
        String descricao
        ) {

}
