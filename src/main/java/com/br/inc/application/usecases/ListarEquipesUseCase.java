package com.br.inc.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import com.br.inc.application.dtos.EquipeResponseDTO;
import com.br.inc.application.gateways.EquipeGateway;

/**
 * Caso de Uso responsável por listar todas as equipes.
 */
public class ListarEquipesUseCase {

    private final EquipeGateway equipeGateway;

    public ListarEquipesUseCase(EquipeGateway equipeGateway) {
        this.equipeGateway = equipeGateway;
    }

    /**
     * Retorna a lista de todas as equipes cadastradas.
     *
     * @return Lista de EquipeResponseDTO.
     */
    public List<EquipeResponseDTO> executar() {
        return equipeGateway.listarTodos().stream()
                .map(EquipeResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
