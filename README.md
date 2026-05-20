# Sistema de Gestão de Projetos e Equipes 🚀

Um sistema robusto para otimizar o controle de projetos corporativos, garantindo a gestão eficaz de equipes, tarefas e o acompanhamento prático de entregas. Desenvolvido para resolver o desafio de alocação de profissionais (como desenvolvedores, analistas e designers) em múltiplas frentes de trabalho, visando o cumprimento de prazos contratuais e o melhor aproveitamento do corpo técnico.

## 🛠️ Stack e Tecnologias

* **Linguagem:** Java 25 LTS 
* **Framework:** Spring Boot 4.0.4
* **Gerenciador de Dependências:** Maven (pacote com.br.inc)
* **Banco de Dados (Fase 1 - 100% Local):** H2 Database (Persistido em arquivo)
* **Documentação:** Swagger / SpringDoc OpenAPI 2.0

## 🏗️ Arquitetura e Padrões

O projeto foi estruturado com foco absoluto no isolamento das regras de negócio, facilidade de manutenção e escalabilidade, utilizando:

* **Clean Architecture Estrita:** Divisão clara do código em 4 camadas fundamentais (Domain, Application, Infrastructure, Presentation).
* **Domain-Driven Design (DDD):** Modelagem focada no domínio central, garantindo que as regras de negócio puras não tenham dependências de frameworks externos ou do banco de dados.
* **Princípios POO (SOLID & Clean Code):** Uso intenso de abstração, encapsulamento rigoroso do estado dos objetos e injeção de dependências.

## ⚙️ Funcionalidades Principais (Core Domain)

* **Gestão de Usuários:** Cadastro de informações essenciais (nome, CPF, e-mail, cargo) e controle de perfis de acesso sistêmico (Administrador, Gerente ou Colaborador).
* **Gestão de Projetos:** Acompanhamento do ciclo de vida com status definidos (Planejado, Em andamento, Concluído, Cancelado), controle de datas de entrega e atribuição direta de um gerente responsável.
* **Alocação de Equipes:** Criação de times com múltiplos membros vinculados, arquitetado para permitir que uma mesma equipe atue de forma dinâmica em vários projetos simultâneos.

---

## 📖 Documentação da API (Swagger/OpenAPI)

A documentação interativa da API está disponível via **Swagger UI** após a inicialização do projeto em:
http://localhost:8080/swagger-ui.html

A documentação detalha os endpoints de:
- **Usuários:** Criação e gestão.
- **Projetos:** Criação e alteração de status.
- **Equipes:** Criação e alocação de membros.

---

*Pronto para rodar localmente. Clone o repositório, instale as dependências via Maven (mvn clean compile) e inicie a aplicação com mvn spring-boot:run.*
