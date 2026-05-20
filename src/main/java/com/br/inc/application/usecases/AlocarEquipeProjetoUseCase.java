package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class AlocarEquipeProjetoUseCase {

    private final ProjetoGateway projetoGateway;
    private final EquipeGateway equipeGateway;

    public AlocarEquipeProjetoUseCase(ProjetoGateway projetoGateway, EquipeGateway equipeGateway) {
        this.projetoGateway = projetoGateway;
        this.equipeGateway = equipeGateway;
    }

    public void executar(Long projetoId, Long equipeId) {
        Projeto projeto = projetoGateway.buscarPorId(projetoId)
                .orElseThrow(() -> new DomainException("Projeto não encontrado com ID: " + projetoId));

        Equipe equipe = equipeGateway.buscarPorId(equipeId)
                .orElseThrow(() -> new DomainException("Equipe não encontrada com ID: " + equipeId));

        projeto.adicionarEquipe(equipe);
        projetoGateway.salvar(projeto);
    }
}
