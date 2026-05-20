package com.br.inc.application.dtos;

import java.util.List;
import java.util.Map;

public record RelatorioProjetoDTO(
        ProjetoResponseDTO projeto,
        List<String> nomesEquipes,
        int totalTarefas,
        double percentualConcluido,
        boolean atrasado,
        Map<String, Long> rankingEquipes,
        Map<String, Long> rankingColaboradores
        ) {

}
