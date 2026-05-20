package com.br.inc.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.inc.application.dtos.TarefaRequestDTO;
import com.br.inc.application.dtos.TarefaResponseDTO;
import com.br.inc.application.usecases.AtualizarStatusTarefaUseCase;
import com.br.inc.application.usecases.AtualizarTarefaUseCase;
import com.br.inc.application.usecases.CriarTarefaUseCase;
import com.br.inc.application.usecases.DeletarTarefaUseCase;
import com.br.inc.application.usecases.ListarTarefasUseCase;
import com.br.inc.domain.enums.StatusTarefa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para gestão de tarefas dos projetos")
public class TarefaController {

    private final CriarTarefaUseCase criarTarefaUseCase;
    private final AtualizarTarefaUseCase atualizarTarefaUseCase;
    private final DeletarTarefaUseCase deletarTarefaUseCase;
    private final ListarTarefasUseCase listarTarefasUseCase;
    private final AtualizarStatusTarefaUseCase atualizarStatusTarefaUseCase;

    public TarefaController(
            CriarTarefaUseCase criarTarefaUseCase,
            AtualizarTarefaUseCase atualizarTarefaUseCase,
            DeletarTarefaUseCase deletarTarefaUseCase,
            ListarTarefasUseCase listarTarefasUseCase,
            AtualizarStatusTarefaUseCase atualizarStatusTarefaUseCase) {
        this.criarTarefaUseCase = criarTarefaUseCase;
        this.atualizarTarefaUseCase = atualizarTarefaUseCase;
        this.deletarTarefaUseCase = deletarTarefaUseCase;
        this.listarTarefasUseCase = listarTarefasUseCase;
        this.atualizarStatusTarefaUseCase = atualizarStatusTarefaUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova tarefa", description = "Cadastra uma tarefa vinculada a um projeto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<TarefaResponseDTO> criar(@RequestBody TarefaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarTarefaUseCase.executar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar tarefas", description = "Retorna uma lista de tarefas. Pode filtrar por projeto.")
    public ResponseEntity<List<TarefaResponseDTO>> listar(@RequestParam(required = false) Long projetoId) {
        return ResponseEntity.ok(listarTarefasUseCase.executar(projetoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id, @RequestBody TarefaRequestDTO dto) {
        return ResponseEntity.ok(atualizarTarefaUseCase.executar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status da tarefa", description = "Atualiza o status de uma tarefa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<TarefaResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusTarefa novoStatus) {
        return ResponseEntity.ok(atualizarStatusTarefaUseCase.executar(id, novoStatus));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarTarefaUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
