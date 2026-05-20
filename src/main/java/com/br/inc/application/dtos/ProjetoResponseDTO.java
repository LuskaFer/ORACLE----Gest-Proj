package com.br.inc.application.dtos;

import java.time.LocalDate;

import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.enums.StatusProjeto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para retorno de dados de um projeto.
 */
public record ProjetoResponseDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Novo Sistema de Vendas")
        String nome,
        @Schema(example = "Desenvolvimento de plataforma web para vendas online")
        String descricao,
        @Schema(example = "2026-06-01")
        LocalDate dataInicio,
        @Schema(example = "2026-12-31")
        LocalDate dataTerminoPrevista,
        @Schema(example = "PLANEJADO")
        StatusProjeto status,
        @Schema(example = "João da Silva")
        String nomeGerente
        ) {

    public static ProjetoResponseDTO fromDomain(Projeto p) {
        return new ProjetoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getDataInicio(),
                p.getDataTerminoPrevista(),
                p.getStatus(),
                p.getGerente().getNomeCompleto()
        );
    }
}
