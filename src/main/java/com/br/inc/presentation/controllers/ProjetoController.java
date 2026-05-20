package com.br.inc.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.inc.application.dtos.ProjetoRequestDTO;
import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.usecases.AlterarStatusProjetoUseCase;
import com.br.inc.application.usecases.CriarProjetoUseCase;
import com.br.inc.domain.enums.StatusProjeto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projetos")
@Tag(name = "Projetos", description = "Endpoints para gestão de projetos")
public class ProjetoController {

    private final CriarProjetoUseCase criarProjetoUseCase;
    private final AlterarStatusProjetoUseCase alterarStatusProjetoUseCase;

    public ProjetoController(CriarProjetoUseCase criarProjetoUseCase, AlterarStatusProjetoUseCase alterarStatusProjetoUseCase) {
        this.criarProjetoUseCase = criarProjetoUseCase;
        this.alterarStatusProjetoUseCase = alterarStatusProjetoUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar um novo projeto", description = "Cadastra um projeto associando a um gerente válido.")
    public ResponseEntity<ProjetoResponseDTO> criar(@RequestBody ProjetoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarProjetoUseCase.executar(dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status do projeto", description = "Atualiza o status de um projeto existente.")
    public ResponseEntity<ProjetoResponseDTO> alterarStatus(@PathVariable Long id, @RequestParam StatusProjeto novoStatus) {
        return ResponseEntity.ok(alterarStatusProjetoUseCase.executar(id, novoStatus));
    }
}
