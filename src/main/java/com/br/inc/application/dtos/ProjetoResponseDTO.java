package com.br.inc.application.dtos;

import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.enums.StatusProjeto;
import java.time.LocalDate;

/**
 * DTO para retorno de dados de um projeto.
 */
public record ProjetoResponseDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataTerminoPrevista,
        StatusProjeto status,
        String nomeGerente
        ) {

    public static ProjetoResponseDTO fromEntity(Projeto p) {
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
