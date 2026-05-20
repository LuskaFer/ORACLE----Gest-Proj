package com.br.inc.infrastructure.gateways.impl;

import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.infrastructure.persistence.entities.UsuarioJpaEntity;
import com.br.inc.infrastructure.persistence.repositories.UsuarioRepository;
import java.util.Optional;

public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;

    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioJpaEntity entity = UsuarioJpaEntity.fromDomain(usuario);
        UsuarioJpaEntity saved = usuarioRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf).map(UsuarioJpaEntity::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioJpaEntity::toDomain);
    }
}
