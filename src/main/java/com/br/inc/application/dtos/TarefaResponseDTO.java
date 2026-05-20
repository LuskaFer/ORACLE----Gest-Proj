package com.br.inc.application.dtos;

import java.time.LocalDate;

import com.br.inc.domain.enums.StatusTarefa;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDate prazo,
        StatusTarefa status,
        Long projetoId,
        Long equipeId,
        Long colaboradorId
        ) {

}
