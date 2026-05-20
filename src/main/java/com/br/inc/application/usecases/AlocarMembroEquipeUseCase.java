package com.br.inc.application.usecases;

import com.br.inc.application.dtos.EquipeResponseDTO;
import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso para alocar um membro em uma equipe.
 */
public class AlocarMembroEquipeUseCase {

    private final EquipeGateway equipeGateway;
    private final UsuarioGateway usuarioGateway;

    public AlocarMembroEquipeUseCase(EquipeGateway equipeGateway, UsuarioGateway usuarioGateway) {
        this.equipeGateway = equipeGateway;
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @param equipeId ID da equipe.
     * @param usuarioId ID do usuário a ser alocado.
     * @return Dados da equipe atualizados.
     */
    public EquipeResponseDTO executar(Long equipeId, Long usuarioId) {
        Equipe equipe = equipeGateway.buscarPorId(equipeId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipe não encontrada."));

        Usuario usuario = usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        equipe.adicionarMembro(usuario);

        Equipe equipeSalva = equipeGateway.salvar(equipe);
        return EquipeResponseDTO.fromDomain(equipeSalva);
    }
}
