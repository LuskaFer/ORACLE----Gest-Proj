package com.br.inc.infrastructure.config;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.application.usecases.AlocarMembroEquipeUseCase;
import com.br.inc.application.usecases.CriarProjetoUseCase;
import com.br.inc.application.usecases.CriarUsuarioUseCase;
import com.br.inc.infrastructure.gateways.impl.EquipeGatewayImpl;
import com.br.inc.infrastructure.gateways.impl.ProjetoGatewayImpl;
import com.br.inc.infrastructure.gateways.impl.UsuarioGatewayImpl;
import com.br.inc.infrastructure.persistence.repositories.EquipeRepository;
import com.br.inc.infrastructure.persistence.repositories.ProjetoRepository;
import com.br.inc.infrastructure.persistence.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    UsuarioGateway usuarioGateway(UsuarioRepository repository) {
        return new UsuarioGatewayImpl(repository);
    }

    @Bean
    ProjetoGateway projetoGateway(ProjetoRepository repository) {
        return new ProjetoGatewayImpl(repository);
    }

    @Bean
    EquipeGateway equipeGateway(EquipeRepository repository) {
        return new EquipeGatewayImpl(repository);
    }

    @Bean
    CriarUsuarioUseCase criarUsuarioUseCase(UsuarioGateway gateway) {
        return new CriarUsuarioUseCase(gateway);
    }

    @Bean
    CriarProjetoUseCase criarProjetoUseCase(ProjetoGateway projetoGateway, UsuarioGateway usuarioGateway) {
        return new CriarProjetoUseCase(projetoGateway, usuarioGateway);
    }

    @Bean
    AlocarMembroEquipeUseCase alocarMembroEquipeUseCase(EquipeGateway equipeGateway, UsuarioGateway usuarioGateway) {
        return new AlocarMembroEquipeUseCase(equipeGateway, usuarioGateway);
    }
}
