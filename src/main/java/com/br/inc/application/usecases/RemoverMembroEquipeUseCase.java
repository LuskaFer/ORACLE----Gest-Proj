package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class RemoverMembroEquipeUseCase {

    private final EquipeGateway equipeGateway;
    private final UsuarioGateway usuarioGateway;

    public RemoverMembroEquipeUseCase(EquipeGateway equipeGateway, UsuarioGateway usuarioGateway) {
        this.equipeGateway = equipeGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public void executar(Long equipeId, Long usuarioId) {
        Equipe equipe = equipeGateway.buscarPorId(equipeId)
                .orElseThrow(() -> new DomainException("Equipe não encontrada com ID: " + equipeId));

        Usuario usuario = usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(() -> new DomainException("Usuário não encontrado com ID: " + usuarioId));

        equipe.removerMembro(usuario);
        equipeGateway.salvar(equipe);
    }
}
