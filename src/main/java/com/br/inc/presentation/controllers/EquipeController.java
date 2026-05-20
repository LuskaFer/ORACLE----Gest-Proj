package com.br.inc.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.inc.application.dtos.EquipeRequestDTO;
import com.br.inc.application.dtos.EquipeResponseDTO;
import com.br.inc.application.usecases.AlocarMembroEquipeUseCase;
import com.br.inc.application.usecases.CriarEquipeUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/equipes")
@Tag(name = "Equipes", description = "Endpoints para gestão de equipes e alocação")
public class EquipeController {

    private final AlocarMembroEquipeUseCase alocarMembroEquipeUseCase;
    private final CriarEquipeUseCase criarEquipeUseCase;

    public EquipeController(AlocarMembroEquipeUseCase alocarMembroEquipeUseCase, CriarEquipeUseCase criarEquipeUseCase) {
        this.alocarMembroEquipeUseCase = alocarMembroEquipeUseCase;
        this.criarEquipeUseCase = criarEquipeUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova equipe", description = "Cadastra uma equipe no sistema.")
    public ResponseEntity<EquipeResponseDTO> criar(@RequestBody EquipeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarEquipeUseCase.executar(dto));
    }

    @PostMapping("/{equipeId}/membros/{usuarioId}")
    @Operation(summary = "Alocar membro na equipe", description = "Adiciona um usuário existente a uma equipe.")
    public ResponseEntity<EquipeResponseDTO> alocarMembro(@PathVariable Long equipeId, @PathVariable Long usuarioId) {
        return ResponseEntity.ok(alocarMembroEquipeUseCase.executar(equipeId, usuarioId));
    }
}
