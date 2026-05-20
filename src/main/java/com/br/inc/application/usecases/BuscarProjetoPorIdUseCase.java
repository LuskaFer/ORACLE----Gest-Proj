package com.br.inc.application.usecases;

import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso responsável por buscar um projeto específico pelo ID.
 */
public class BuscarProjetoPorIdUseCase {

    private final ProjetoGateway projetoGateway;

    public BuscarProjetoPorIdUseCase(ProjetoGateway projetoGateway) {
        this.projetoGateway = projetoGateway;
    }

    /**
     * Busca um projeto e retorna seu DTO.
     *
     * @param id Identificador do projeto.
     * @return ProjetoResponseDTO.
     * @throws RecursoNaoEncontradoException se o projeto não existir.
     */
    public ProjetoResponseDTO executar(Long id) {
        return projetoGateway.buscarPorId(id)
                .map(ProjetoResponseDTO::fromDomain)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com o ID: " + id));
    }
}
