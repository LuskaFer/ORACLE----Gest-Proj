package com.br.inc.application.usecases;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.dtos.RelatorioProjetoDTO;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.entities.Tarefa;
import com.br.inc.domain.enums.StatusProjeto;
import com.br.inc.domain.enums.StatusTarefa;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class GerarRelatorioProjetoUseCase {

    private final ProjetoGateway projetoGateway;
    private final TarefaGateway tarefaGateway;

    public GerarRelatorioProjetoUseCase(ProjetoGateway projetoGateway, TarefaGateway tarefaGateway) {
        this.projetoGateway = projetoGateway;
        this.tarefaGateway = tarefaGateway;
    }

    public RelatorioProjetoDTO executar(Long projetoId) {
        Projeto projeto = projetoGateway.buscarPorId(projetoId)
                .orElseThrow(() -> new DomainException("Projeto não encontrado com ID: " + projetoId));

        List<Tarefa> tarefas = tarefaGateway.listarPorProjeto(projetoId);
        List<String> nomesEquipes = projeto.getEquipes().stream()
                .map(e -> e.getNome())
                .collect(Collectors.toList());

        int totalTarefas = tarefas.size();
        double percentualConcluido = projeto.calcularPercentualConcluido(tarefas);

        boolean atrasado = projeto.getDataTerminoPrevista().isBefore(LocalDate.now())
                && projeto.getStatus() != StatusProjeto.CONCLUIDO;

        Map<String, Long> rankingEquipes = ordenarRanking(tarefas.stream()
                .filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA)
                .map(Tarefa::getEquipeResponsavel)
                .filter(e -> e != null)
                .map(e -> e.getNome())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        Map<String, Long> rankingColaboradores = ordenarRanking(tarefas.stream()
                .filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA)
                .map(Tarefa::getColaboradorResponsavel)
                .filter(u -> u != null)
                .map(u -> u.getNomeCompleto())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        ProjetoResponseDTO projetoDTO = new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataTerminoPrevista(),
                projeto.getStatus(),
                projeto.getGerente().getNomeCompleto()
        );

        return new RelatorioProjetoDTO(
                projetoDTO,
                nomesEquipes,
                totalTarefas,
                percentualConcluido,
                atrasado,
                rankingEquipes,
                rankingColaboradores
        );
    }

    private Map<String, Long> ordenarRanking(Map<String, Long> ranking) {
        return ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
