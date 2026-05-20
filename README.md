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

## 📦 Bibliotecas Utilizadas

- **Spring Boot Starter Web:** API REST e controllers.
- **Spring Boot Starter Data JPA:** Persistencia e mapeamento ORM.
- **H2 Database:** Banco local em arquivo para desenvolvimento.
- **Spring Boot Starter Validation:** Validacoes de entrada.
- **Lombok:** Reducao de boilerplate (quando aplicavel).
- **SpringDoc OpenAPI UI:** Swagger UI e documentacao OpenAPI.
- **Spring Boot Starter Test:** Testes com JUnit 5 e suporte Spring.

## ⚙️ Funcionalidades Principais (Core Domain)

* **Gestão de Usuários:** Cadastro de informações essenciais (nome, CPF, e-mail, cargo) e controle de perfis de acesso sistêmico (Administrador, Gerente ou Colaborador).
* **Gestão de Projetos:** Acompanhamento do ciclo de vida com status definidos (Planejado, Em andamento, Concluído, Cancelado), controle de datas de entrega e atribuição direta de um gerente responsável.
* **Alocação de Equipes:** Criação de times com múltiplos membros vinculados, arquitetado para permitir que uma mesma equipe atue de forma dinâmica em vários projetos simultâneos.
* **Gestão de Tarefas:** CRUD completo de tarefas com status (Pendente, Em execução, Concluída), vínculo obrigatório a projeto e equipe, e vínculo opcional a um colaborador responsável.
* **Relatório de Desempenho:** Indicadores do projeto com percentual de tarefas concluídas, flag de atraso e rankings de equipes/colaboradores por tarefas concluídas.

## ✅ Regras de Negocio e Validacoes

### Usuario
- Nome completo obrigatorio.
- CPF deve conter 11 digitos.
- Email deve conter "@".
- Login obrigatorio.
- Senha com minimo de 6 caracteres.
- Perfil obrigatorio (ADMINISTRADOR, GERENTE, COLABORADOR).

### Equipe
- Nome obrigatorio.
- Nao permite adicionar o mesmo membro duas vezes.
- Remover membro inexistente gera erro.

### Projeto
- Nome com minimo de 3 caracteres.
- Datas de inicio e termino obrigatorias.
- Data de termino prevista nao pode ser anterior a data de inicio.
- Gerente obrigatorio.
- Nao permite alterar status quando estiver CONCLUIDO ou CANCELADO.
- Nao permite alocar a mesma equipe duas vezes.
- Remover equipe nao alocada gera erro.

### Tarefa
- Titulo com minimo de 3 caracteres.
- Prazo obrigatorio.
- Projeto obrigatorio.
- Equipe responsavel obrigatoria.
- Equipe responsavel deve estar alocada ao projeto.
- Colaborador responsavel e opcional, mas deve pertencer a equipe responsavel.
- Status nao pode ser nulo.

### Relatorio de Projeto
- Percentual de concluidas = (tarefas concluidas / total) * 100.
- Projeto atrasado quando data prevista < hoje e status diferente de CONCLUIDO.
- Ranking de equipes e colaboradores baseado em tarefas concluidas.

---

## 📖 Documentação da API (Swagger/OpenAPI)

A documentação interativa da API está disponível via **Swagger UI** após a inicialização do projeto em:
http://localhost:8080/swagger-ui.html

A documentação detalha os endpoints de:
- **Usuários:** Criação e gestão.
- **Projetos:** Criação e alteração de status.
- **Equipes:** Criação e alocação de membros.
- **Tarefas:** CRUD completo, mudança de status e filtros por projeto.
- **Relatório do Projeto:** Indicadores e rankings de desempenho.

---

## ▶️ Como executar localmente

1. Compile o projeto:
	```bash
	mvn clean compile
	```
2. Suba a aplicação:
	```bash
	mvn spring-boot:run
	```

## 🧪 Como testar a solucao

### 1) Testes automatizados
Execute os testes unitarios do dominio com:
```bash
mvn test
```

### 2) Teste manual via Swagger
1. Acesse o Swagger UI: http://localhost:8080/swagger-ui.html
2. Crie um usuario gerente.
3. Crie uma equipe e aloque membros.
4. Crie um projeto informando o gerente.
5. Aloque a equipe no projeto.
6. Crie tarefas vinculando `projetoId` e `equipeId` (e, se quiser, `colaboradorId`).
7. Altere o status das tarefas para `CONCLUIDA`.
8. Gere o relatorio em `GET /api/projetos/{id}/relatorio`.

### 3) Banco de dados H2 (opcional)
O banco roda em arquivo local e o console esta habilitado em:
http://localhost:8080/h2-console

Use a URL JDBC:
```
jdbc:h2:file:./data/gestao_projetos_db
```

Usuario: `sa`
Senha: `password`

---

*Pronto para rodar localmente. Se preferir, voce pode apenas compilar com `mvn clean compile` e subir com `mvn spring-boot:run`.*
