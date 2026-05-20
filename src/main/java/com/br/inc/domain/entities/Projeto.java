package com.br.inc.domain.entities;

import com.br.inc.domain.enums.StatusProjeto;
import com.br.inc.domain.exceptions.DomainException;
import java.time.LocalDate;

/**
 * Representa um projeto dentro da organização. Segue regras de negócio para
 * transição de datas e status.
 */
public class Projeto {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private StatusProjeto status;
    private Usuario gerente;

    /**
     * Construtor para criação de novos projetos.
     *
     * @param nome Nome do projeto (mínimo 3 caracteres).
     * @param descricao Breve descrição do objetivo.
     * @param dataInicio Data de início (não pode ser no passado distante).
     * @param dataTerminoPrevista Data prevista para conclusão (deve ser
     * posterior ao início).
     * @param gerente Usuário responsável com perfil adequado.
     * @throws DomainException caso as datas ou o gerente sejam inválidos.
     */
    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, Usuario gerente) {
        validarDatas(dataInicio, dataTerminoPrevista);
        if (nome == null || nome.length() < 3) {
            throw new DomainException("Nome do projeto deve ter ao menos 3 caracteres.");
        }
        if (gerente == null) {
            throw new DomainException("Todo projeto deve possuir um gerente responsável.");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.gerente = gerente;
        this.status = StatusProjeto.PLANEJADO;
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new DomainException("Datas de início e término são obrigatórias.");
        }
        if (fim.isBefore(inicio)) {
            throw new DomainException("A data de término prevista não pode ser anterior à data de início.");
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    /**
     * Altera o status do projeto seguindo o fluxo de vida permitido.
     *
     * @param novoStatus {@link StatusProjeto}
     */
    public void atualizarStatus(StatusProjeto novoStatus) {
        if (this.status == StatusProjeto.CONCLUIDO || this.status == StatusProjeto.CANCELADO) {
            throw new DomainException("Não é possível alterar o status de um projeto finalizado ou cancelado.");
        }
        this.status = novoStatus;
    }
}
