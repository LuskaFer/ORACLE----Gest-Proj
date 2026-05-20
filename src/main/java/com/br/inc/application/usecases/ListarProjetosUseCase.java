package com.br.inc.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.gateways.ProjetoGateway;

/**
 * Caso de Uso responsável por listar todos os projetos.
 */
public class ListarProjetosUseCase {

    private final ProjetoGateway projetoGateway;

    public ListarProjetosUseCase(ProjetoGateway projetoGateway) {
        this.projetoGateway = projetoGateway;
    }

    /**
     * Retorna a lista de todos os projetos cadastrados.
     *
     * @return Lista de ProjetoResponseDTO.
     */
    public List<ProjetoResponseDTO> executar() {
        return projetoGateway.listarTodos().stream()
                .map(ProjetoResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
