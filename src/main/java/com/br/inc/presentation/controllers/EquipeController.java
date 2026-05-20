package com.br.inc.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.inc.application.dtos.EquipeRequestDTO;
import com.br.inc.application.dtos.EquipeResponseDTO;
import com.br.inc.application.usecases.AlocarMembroEquipeUseCase;
import com.br.inc.application.usecases.CriarEquipeUseCase;
import com.br.inc.application.usecases.DeletarEquipeUseCase;
import com.br.inc.application.usecases.ListarEquipesUseCase;
import com.br.inc.application.usecases.RemoverMembroEquipeUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/equipes")
@Tag(name = "Equipes", description = "Endpoints para gestão de equipes e alocação")
public class EquipeController {

    private final AlocarMembroEquipeUseCase alocarMembroEquipeUseCase;
    private final CriarEquipeUseCase criarEquipeUseCase;
    private final ListarEquipesUseCase listarEquipesUseCase;
    private final RemoverMembroEquipeUseCase removerMembroEquipeUseCase;
    private final DeletarEquipeUseCase deletarEquipeUseCase;

    public EquipeController(
            AlocarMembroEquipeUseCase alocarMembroEquipeUseCase,
            CriarEquipeUseCase criarEquipeUseCase,
            ListarEquipesUseCase listarEquipesUseCase,
            RemoverMembroEquipeUseCase removerMembroEquipeUseCase,
            DeletarEquipeUseCase deletarEquipeUseCase) {
        this.alocarMembroEquipeUseCase = alocarMembroEquipeUseCase;
        this.criarEquipeUseCase = criarEquipeUseCase;
        this.listarEquipesUseCase = listarEquipesUseCase;
        this.removerMembroEquipeUseCase = removerMembroEquipeUseCase;
        this.deletarEquipeUseCase = deletarEquipeUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova equipe", description = "Cadastra uma equipe no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipe criada com sucesso")
    })
    public ResponseEntity<EquipeResponseDTO> criar(@RequestBody EquipeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarEquipeUseCase.executar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas as equipes", description = "Retorna uma lista de todas as equipes cadastradas.")
    public ResponseEntity<List<EquipeResponseDTO>> listar() {
        return ResponseEntity.ok(listarEquipesUseCase.executar());
    }

    @PostMapping("/{equipeId}/membros/{usuarioId}")
    @Operation(summary = "Alocar membro na equipe", description = "Adiciona um usuário existente a uma equipe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Membro alocado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipe ou Usuário não encontrado")
    })
    public ResponseEntity<EquipeResponseDTO> alocarMembro(@PathVariable Long equipeId, @PathVariable Long usuarioId) {
        return ResponseEntity.ok(alocarMembroEquipeUseCase.executar(equipeId, usuarioId));
    }

    @DeleteMapping("/{equipeId}/membros/{usuarioId}")
    @Operation(summary = "Remover membro da equipe", description = "Remove um usuário existente de uma equipe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Membro removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipe ou Usuário não encontrado")
    })
    public ResponseEntity<Void> removerMembro(@PathVariable Long equipeId, @PathVariable Long usuarioId) {
        removerMembroEquipeUseCase.executar(equipeId, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar equipe", description = "Remove uma equipe do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipe deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipe não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarEquipeUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
