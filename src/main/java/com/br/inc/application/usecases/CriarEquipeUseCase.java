package com.br.inc.application.usecases;

import com.br.inc.application.dtos.EquipeRequestDTO;
import com.br.inc.application.dtos.EquipeResponseDTO;
import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.domain.entities.Equipe;

/**
 * Caso de uso para criação de uma nova equipe.
 */
public class CriarEquipeUseCase {

    private final EquipeGateway equipeGateway;

    public CriarEquipeUseCase(EquipeGateway equipeGateway) {
        this.equipeGateway = equipeGateway;
    }

    /**
     * Executa a criação da equipe.
     *
     * @param dto Dados da equipe.
     * @return DTO da equipe criada.
     */
    public EquipeResponseDTO executar(EquipeRequestDTO dto) {
        Equipe equipe = new Equipe(dto.nome(), dto.descricao());
        Equipe equipeSalva = equipeGateway.salvar(equipe);
        return EquipeResponseDTO.fromDomain(equipeSalva);
    }
}
