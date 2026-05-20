package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class DeletarProjetoUseCase {

    private final ProjetoGateway projetoGateway;

    public DeletarProjetoUseCase(ProjetoGateway projetoGateway) {
        this.projetoGateway = projetoGateway;
    }

    public void executar(Long id) {
        projetoGateway.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Projeto não encontrado com ID: " + id));
        projetoGateway.deletarPorId(id);
    }
}
