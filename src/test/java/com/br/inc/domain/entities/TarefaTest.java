package com.br.inc.domain.entities;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.enums.StatusTarefa;
import com.br.inc.domain.exceptions.DomainException;

class TarefaTest {

    private Projeto projeto;
    private Usuario gerente;
    private Equipe equipe;
    private Usuario colaborador;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        gerente = new Usuario("Gerente", "11111111111", "gerente@email.com", "Gerente", "gerente", "senha123", PerfilUsuario.GERENTE);
        projeto = new Projeto("Projeto Teste", "Descricao", LocalDate.now(), LocalDate.now().plusDays(10), gerente);
        equipe = new Equipe("Equipe Dev", "Time de desenvolvimento");
        colaborador = new Usuario("Colaborador", "22222222222", "colab@email.com", "Dev", "colab", "senha123", PerfilUsuario.COLABORADOR);
        equipe.adicionarMembro(colaborador);
        projeto.adicionarEquipe(equipe);
    }

    @Test
    @DisplayName("Deve criar uma tarefa válida")
    void deveCriarTarefaValida() {
        Tarefa tarefa = new Tarefa("Tarefa 1", "Descricao", LocalDate.now().plusDays(5), projeto, equipe, colaborador);
        assertNotNull(tarefa);
        assertEquals("Tarefa 1", tarefa.getTitulo());
        assertEquals(StatusTarefa.PENDENTE, tarefa.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar tarefa com título curto")
    void deveLancarExcecaoTituloCurto() {
        DomainException exception = assertThrows(DomainException.class, () -> new Tarefa("Ta", "Desc", LocalDate.now(), projeto, equipe, colaborador));
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve permitir atualizar status da tarefa")
    void deveAtualizarStatus() {
        Tarefa tarefa = new Tarefa("Tarefa 1", "Descricao", LocalDate.now(), projeto, equipe, colaborador);
        tarefa.atualizarStatus(StatusTarefa.CONCLUIDA);
        assertEquals(StatusTarefa.CONCLUIDA, tarefa.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando equipe não estiver alocada ao projeto")
    void deveLancarExcecaoEquipeNaoAlocada() {
        Equipe equipeNaoAlocada = new Equipe("Equipe QA", "Qualidade");
        DomainException exception = assertThrows(DomainException.class, () -> new Tarefa(
                "Tarefa QA",
                "Descricao",
                LocalDate.now().plusDays(2),
                projeto,
                equipeNaoAlocada,
                null
        ));
        assertNotNull(exception);
    }
}
