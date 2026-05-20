package com.br.inc.application.usecases;

import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.enums.StatusProjeto;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de uso para alterar o status de um projeto.
 */
public class AlterarStatusProjetoUseCase {

    private final ProjetoGateway projetoGateway;

    public AlterarStatusProjetoUseCase(ProjetoGateway projetoGateway) {
        this.projetoGateway = projetoGateway;
    }

    /**
     * Altera o status de um projeto existente.
     *
     * @param id ID do projeto.
     * @param novoStatus Novo status desejado.
     * @return DTO do projeto atualizado.
     */
    public ProjetoResponseDTO executar(Long id, StatusProjeto novoStatus) {
        Projeto projeto = projetoGateway.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com id: " + id));

        projeto.atualizarStatus(novoStatus);
        Projeto projetoSalvo = projetoGateway.salvar(projeto);
        return ProjetoResponseDTO.fromDomain(projetoSalvo);
    }
}
