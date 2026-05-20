package com.br.inc.domain.exceptions;

/**
 * Exceção base para erros de regra de negócio da camada de domínio.
 */
public class DomainException extends RuntimeException {

    /**
     * @param message Mensagem detalhando a violação da regra de negócio.
     */
    public DomainException(String message) {
        super(message);
    }
}
