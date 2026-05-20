package com.br.inc.application.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record TarefaRequestDTO(
        @Schema(example = "Desenvolver camada de domínio")
        String titulo,
        @Schema(example = "Criar as entidades e enums necessários para o módulo de tarefas")
        String descricao,
        @Schema(example = "2026-06-15")
        LocalDate prazo,
        @Schema(example = "1")
        Long projetoId,
        @Schema(example = "1")
        Long equipeId,
        @Schema(example = "1")
        Long colaboradorId
        ) {

}
