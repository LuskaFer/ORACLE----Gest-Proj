package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class DeletarEquipeUseCase {

    private final EquipeGateway equipeGateway;

    public DeletarEquipeUseCase(EquipeGateway equipeGateway) {
        this.equipeGateway = equipeGateway;
    }

    public void executar(Long id) {
        equipeGateway.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Equipe não encontrada com ID: " + id));
        equipeGateway.deletarPorId(id);
    }
}
