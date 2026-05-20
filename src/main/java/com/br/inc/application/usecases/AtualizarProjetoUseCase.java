package com.br.inc.application.usecases;

import com.br.inc.application.dtos.ProjetoRequestDTO;
import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso responsável por atualizar os dados de um projeto existente.
 */
public class AtualizarProjetoUseCase {

    private final ProjetoGateway projetoGateway;
    private final UsuarioGateway usuarioGateway;

    public AtualizarProjetoUseCase(ProjetoGateway projetoGateway, UsuarioGateway usuarioGateway) {
        this.projetoGateway = projetoGateway;
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Atualiza os dados de um projeto.
     *
     * @param id Identificador do projeto.
     * @param dto Novos dados do projeto.
     * @return ProjetoResponseDTO atualizado.
     */
    public ProjetoResponseDTO executar(Long id, ProjetoRequestDTO dto) {
        Projeto projeto = projetoGateway.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com o ID: " + id));

        Usuario gerente = usuarioGateway.buscarPorId(dto.gerenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gerente não encontrado com o ID: " + dto.gerenteId()));

        projeto.atualizarDados(
                dto.nome(),
                dto.descricao(),
                dto.dataInicio(),
                dto.dataTerminoPrevista(),
                gerente
        );

        Projeto atualizado = projetoGateway.salvar(projeto);
        return ProjetoResponseDTO.fromDomain(atualizado);
    }
}
