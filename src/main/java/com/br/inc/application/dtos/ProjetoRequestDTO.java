package com.br.inc.application.dtos;

import java.time.LocalDate;

/**
 * DTO para solicitação de criação de um novo projeto.
 */
public record ProjetoRequestDTO(
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataTerminoPrevista,
        Long gerenteId
        ) {

}
