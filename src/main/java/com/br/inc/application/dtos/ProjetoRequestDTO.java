package com.br.inc.application.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para solicitação de criação ou atualização de um novo projeto.
 */
public record ProjetoRequestDTO(
        @Schema(example = "Novo Sistema de Vendas")
        String nome,
        @Schema(example = "Desenvolvimento de plataforma web para vendas online")
        String descricao,
        @Schema(example = "2026-06-01")
        LocalDate dataInicio,
        @Schema(example = "2026-12-31")
        LocalDate dataTerminoPrevista,
        @Schema(example = "1")
        Long gerenteId
        ) {

}
