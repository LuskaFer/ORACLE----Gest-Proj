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

import com.br.inc.application.dtos.ProjetoRequestDTO;
import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.dtos.RelatorioProjetoDTO;
import com.br.inc.application.usecases.AlocarEquipeProjetoUseCase;
import com.br.inc.application.usecases.AlterarStatusProjetoUseCase;
import com.br.inc.application.usecases.AtualizarProjetoUseCase;
import com.br.inc.application.usecases.BuscarProjetoPorIdUseCase;
import com.br.inc.application.usecases.CriarProjetoUseCase;
import com.br.inc.application.usecases.DeletarProjetoUseCase;
import com.br.inc.application.usecases.GerarRelatorioProjetoUseCase;
import com.br.inc.application.usecases.ListarProjetosUseCase;
import com.br.inc.application.usecases.RemoverEquipeProjetoUseCase;
import com.br.inc.domain.enums.StatusProjeto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projetos")
@Tag(name = "Projetos", description = "Endpoints para gestão de projetos")
public class ProjetoController {

    private final CriarProjetoUseCase criarProjetoUseCase;
    private final AlterarStatusProjetoUseCase alterarStatusProjetoUseCase;
    private final ListarProjetosUseCase listarProjetosUseCase;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private final AtualizarProjetoUseCase atualizarProjetoUseCase;
    private final DeletarProjetoUseCase deletarProjetoUseCase;
    private final AlocarEquipeProjetoUseCase alocarEquipeProjetoUseCase;
    private final RemoverEquipeProjetoUseCase removerEquipeProjetoUseCase;
    private final GerarRelatorioProjetoUseCase gerarRelatorioProjetoUseCase;

    public ProjetoController(
            CriarProjetoUseCase criarProjetoUseCase,
            AlterarStatusProjetoUseCase alterarStatusProjetoUseCase,
            ListarProjetosUseCase listarProjetosUseCase,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            AtualizarProjetoUseCase atualizarProjetoUseCase,
            DeletarProjetoUseCase deletarProjetoUseCase,
            AlocarEquipeProjetoUseCase alocarEquipeProjetoUseCase,
            RemoverEquipeProjetoUseCase removerEquipeProjetoUseCase,
            GerarRelatorioProjetoUseCase gerarRelatorioProjetoUseCase) {
        this.criarProjetoUseCase = criarProjetoUseCase;
        this.alterarStatusProjetoUseCase = alterarStatusProjetoUseCase;
        this.listarProjetosUseCase = listarProjetosUseCase;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
        this.atualizarProjetoUseCase = atualizarProjetoUseCase;
        this.deletarProjetoUseCase = deletarProjetoUseCase;
        this.alocarEquipeProjetoUseCase = alocarEquipeProjetoUseCase;
        this.removerEquipeProjetoUseCase = removerEquipeProjetoUseCase;
        this.gerarRelatorioProjetoUseCase = gerarRelatorioProjetoUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar um novo projeto", description = "Cadastra um projeto associando a um gerente válido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Gerente não encontrado")
    })
    public ResponseEntity<ProjetoResponseDTO> criar(@RequestBody ProjetoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarProjetoUseCase.executar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os projetos", description = "Retorna uma lista de todos os projetos cadastrados.")
    public ResponseEntity<List<ProjetoResponseDTO>> listar() {
        return ResponseEntity.ok(listarProjetosUseCase.executar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID", description = "Retorna os detalhes de um projeto específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buscarProjetoPorIdUseCase.executar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar projeto", description = "Atualiza os dados básicos de um projeto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto ou Gerente não encontrado")
    })
    public ResponseEntity<ProjetoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProjetoRequestDTO dto) {
        return ResponseEntity.ok(atualizarProjetoUseCase.executar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status do projeto", description = "Atualiza o status de um projeto existente seguindo o fluxo de vida.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Transição de status inválida"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<ProjetoResponseDTO> alterarStatus(@PathVariable Long id, @RequestParam StatusProjeto novoStatus) {
        return ResponseEntity.ok(alterarStatusProjetoUseCase.executar(id, novoStatus));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar projeto", description = "Remove um projeto do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Projeto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarProjetoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projetoId}/equipes/{equipeId}")
    @Operation(summary = "Alocar equipe no projeto", description = "Vincula uma equipe a um projeto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipe alocada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto ou Equipe não encontrada")
    })
    public ResponseEntity<Void> alocarEquipe(@PathVariable Long projetoId, @PathVariable Long equipeId) {
        alocarEquipeProjetoUseCase.executar(projetoId, equipeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projetoId}/equipes/{equipeId}")
    @Operation(summary = "Remover equipe do projeto", description = "Desvincula uma equipe de um projeto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipe removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto ou Equipe não encontrada")
    })
    public ResponseEntity<Void> removerEquipe(@PathVariable Long projetoId, @PathVariable Long equipeId) {
        removerEquipeProjetoUseCase.executar(projetoId, equipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/relatorio")
    @Operation(summary = "Gerar relatório do projeto", description = "Retorna indicadores de desempenho e status do projeto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<RelatorioProjetoDTO> gerarRelatorio(@PathVariable Long id) {
        return ResponseEntity.ok(gerarRelatorioProjetoUseCase.executar(id));
    }
}
