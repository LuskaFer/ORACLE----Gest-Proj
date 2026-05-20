package com.br.inc.domain.entities;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.enums.StatusProjeto;
import com.br.inc.domain.enums.StatusTarefa;
import com.br.inc.domain.exceptions.DomainException;

class ProjetoTest {

    private Usuario gerente;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        gerente = new Usuario("Gerente", "111.111.111-11", "gerente@email.com", "Gerente de TI", "ger", "senha123", PerfilUsuario.GERENTE);
    }

    @Test
    @DisplayName("Deve criar um projeto válido")
    void deveCriarProjetoValido() {
        Projeto projeto = new Projeto(
                "Projeto Alpha",
                "Descrição do projeto",
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                gerente
        );

        assertNotNull(projeto);
        assertEquals(StatusProjeto.PLANEJADO, projeto.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar projeto com data de término anterior ao início")
    void deveLancarExcecaoDatasInvalidas() {
        DomainException exception = assertThrows(DomainException.class, () -> new Projeto(
                "Projeto",
                "Desc",
                LocalDate.now().plusDays(10),
                LocalDate.now(),
                gerente
        ));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve permitir alterar status do projeto")
    void deveAlterarStatus() {
        Projeto projeto = new Projeto("Projeto", "Desc", LocalDate.now(), LocalDate.now().plusDays(1), gerente);

        projeto.atualizarStatus(StatusProjeto.EM_ANDAMENTO);
        assertEquals(StatusProjeto.EM_ANDAMENTO, projeto.getStatus());
    }

    @Test
    @DisplayName("Não deve permitir alterar status de projeto concluído")
    void naoDeveAlterarStatusConcluido() {
        Projeto projeto = new Projeto("Projeto", "Desc", LocalDate.now(), LocalDate.now().plusDays(1), gerente);
        projeto.atualizarStatus(StatusProjeto.CONCLUIDO);

        DomainException exception = assertThrows(DomainException.class, () -> projeto.atualizarStatus(StatusProjeto.PLANEJADO));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve permitir alocar e remover equipes do projeto")
    void deveAlocarERemoverEquipes() {
        Projeto projeto = new Projeto("Projeto", "Desc", LocalDate.now(), LocalDate.now().plusDays(1), gerente);
        Equipe equipe = new Equipe("Equipe Dev", "Desenvolvimento");

        projeto.adicionarEquipe(equipe);
        assertEquals(1, projeto.getEquipes().size());

        projeto.removerEquipe(equipe);
        assertEquals(0, projeto.getEquipes().size());
    }

    @Test
    @DisplayName("Não deve permitir alocar a mesma equipe duas vezes")
    void naoDeveAlocarEquipeDuplicada() {
        Projeto projeto = new Projeto("Projeto", "Desc", LocalDate.now(), LocalDate.now().plusDays(1), gerente);
        Equipe equipe = new Equipe("Equipe Dev", "Desenvolvimento");

        projeto.adicionarEquipe(equipe);
        DomainException exception = assertThrows(DomainException.class, () -> projeto.adicionarEquipe(equipe));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve calcular percentual de tarefas concluidas")
    void deveCalcularPercentualTarefasConcluidas() {
        Projeto projeto = new Projeto("Projeto", "Desc", LocalDate.now(), LocalDate.now().plusDays(1), gerente);
        Equipe equipe = new Equipe("Equipe Dev", "Desenvolvimento");
        Usuario colaborador = new Usuario("Joao", "22222222222", "joao@email.com", "Dev", "joao", "senha123", PerfilUsuario.COLABORADOR);
        equipe.adicionarMembro(colaborador);
        projeto.adicionarEquipe(equipe);

        Tarefa tarefa1 = new Tarefa("Tarefa 1", "Desc", LocalDate.now().plusDays(1), projeto, equipe, colaborador);
        Tarefa tarefa2 = new Tarefa("Tarefa 2", "Desc", LocalDate.now().plusDays(1), projeto, equipe, colaborador);
        tarefa2.atualizarStatus(StatusTarefa.CONCLUIDA);

        double percentual = projeto.calcularPercentualConcluido(java.util.List.of(tarefa1, tarefa2));

        assertTrue(percentual > 49.9 && percentual < 50.1);
    }
}
