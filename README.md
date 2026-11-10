# Finance API

API RESTful de um sistema financeiro digital, desenvolvida para simular o núcleo de operações de um banco digital ou de uma carteira virtual (*digital wallet/core banking*).

O projeto gerencia usuários, carteiras digitais e transferências entre carteiras. Seu objetivo é demonstrar, em uma aplicação Spring Boot, como separar regras de negócio, casos de uso e persistência usando princípios de Clean Architecture e inversão de dependência.

> **Status:** projeto em desenvolvimento. A documentação descreve o comportamento disponível no código atual e também destaca pontos que ainda precisam ser endurecidos antes de um uso produtivo.

## Sumário

- [O que o projeto resolve](#o-que-o-projeto-resolve)
- [Principais recursos](#principais-recursos)
- [Como o sistema funciona](#como-o-sistema-funciona)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração](#configuração)
- [Executando localmente](#executando-localmente)
- [API HTTP](#api-http)
- [Modelo de dados](#modelo-de-dados)
- [Regras de negócio](#regras-de-negócio)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Testes](#testes)
- [Limitações atuais e próximos passos](#limitações-atuais-e-próximos-passos)

## O que o projeto resolve

O sistema permite cadastrar clientes e movimentar valores entre suas carteiras digitais. Em vez de representar dinheiro em espécie, cada saldo é mantido em uma carteira vinculada a um usuário.

O fluxo de onboarding é automático:

1. Um usuário é cadastrado.
2. O sistema cria uma carteira para esse usuário.
3. A carteira recebe saldo inicial de **R$ 1.000,00**.
4. A carteira pode ser consultada e usada como origem ou destino de transferências.

A transferência realiza o débito na carteira de origem, o crédito na carteira de destino e registra uma transação com data e hora.

## Principais recursos

- Cadastro de usuários com UUID.
- Consulta de um usuário por ID.
- Listagem de usuários.
- Criação automática de uma carteira durante o cadastro.
- Consulta de carteira e saldo.
- Transferência entre duas carteiras.
- Validação de saldo insuficiente.
- Bloqueio de transferência para a mesma carteira.
- Registro de transações.
- Persistência com JPA/Hibernate e MySQL.
- Evolução do schema com Flyway.
- `@Version` na entidade de carteira para suporte a optimistic locking.
- Documentação OpenAPI/Swagger via Springdoc.

## Como o sistema funciona

### Cadastro e onboarding

`CreateUserUseCase` cria um usuário com um UUID, persiste o usuário e, na mesma operação transacional, cria sua carteira com saldo inicial de `1000.00`.

O CPF e o e-mail são campos únicos na entidade JPA e no modelo de banco previsto. A API atualmente recebe `fullName`, `cpf` e `email`.

### Transferência

`TransferUseCase`:

1. Busca a carteira de origem.
2. Busca a carteira de destino.
3. Debita o valor da origem.
4. Credita o valor no destino.
5. Cria o registro de transação.
6. Persiste as duas carteiras e a transação.

O domínio rejeita valores nulos ou menores/iguais a zero, impede transferências para a mesma carteira e lança `InsufficientBalanceException` quando não há saldo suficiente.

### Concorrência e atomicidade

A entidade de persistência `WalletEntity` possui o campo `version` com `@Version`, mecanismo do JPA para detectar atualizações concorrentes. A criação do usuário e o endpoint de transferência usam suporte transacional do Spring.

Esses mecanismos representam a estratégia desejada para evitar saldos inconsistentes e permitir *rollback*. Consulte [Limitações atuais e próximos passos](#limitações-atuais-e-próximos-passos) antes de considerar o fluxo pronto para produção.

## Arquitetura

O projeto segue uma separação em camadas:

```text
Controller (HTTP)
    |
    v
Use Case (aplicação)
    |
    v
Gateway (interfaces)
    |
    v
GatewayImpl / Repository (infraestrutura)
    |
    v
MySQL
```

### Domínio

Contém as entidades e regras independentes de framework:

- `User`
- `Wallet`
- `Transaction`

### Aplicação

Contém os casos de uso e as interfaces (*gateways*) que o domínio/aplicação utiliza:

- Criar usuário.
- Buscar usuário por ID.
- Listar usuários.
- Buscar carteira por ID.
- Transferir valores.

### Infraestrutura

Contém os detalhes externos:

- Controllers REST.
- DTOs de entrada e saída.
- Entidades JPA.
- Repositórios Spring Data.
- Implementações dos gateways.
- Configuração dos casos de uso.
- Flyway e conexão com MySQL.

Os DTOs evitam expor diretamente as entidades de persistência nos contratos de entrada da API.

## Tecnologias

- Java 27
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL 8
- Flyway
- Springdoc OpenAPI
- Lombok
- Maven Wrapper
- JUnit e Spring Boot Test

## Pré-requisitos

- JDK 27 configurado no `PATH`.
- Docker e Docker Compose, ou uma instância MySQL 8 disponível.
- Porta `3306` livre caso o banco seja iniciado pelo Compose.

Confira a versão instalada:

```bash
java -version
./mvnw -version
```

## Configuração

As propriedades podem ser definidas por variáveis de ambiente:

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/wallet_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true` | URL JDBC do MySQL |
| `DB_USER` | `root` | Usuário do banco |
| `DB_PASSWORD` | sem padrão | Senha do banco |

O `docker-compose.yml` cria:

- Banco: `wallet_db`
- Usuário: `root`
- Senha do root: `123`
- Container: `wallet_mysql`
- Porta: `3306`

> Não use credenciais fixas em ambientes reais. Prefira secrets ou variáveis protegidas.

## Executando localmente

### 1. Inicie o MySQL

```bash
docker compose up -d db
```

### 2. Configure a senha da aplicação

```bash
export DB_PASSWORD=123
```

Opcionalmente, sobrescreva usuário e URL:

```bash
export DB_USER=root
export DB_URL='jdbc:mysql://localhost:3306/wallet_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true'
```

### 3. Inicie a API

```bash
./mvnw spring-boot:run
```

A aplicação fica disponível, por padrão, em `http://localhost:8080`.

Para gerar o artefato:

```bash
./mvnw clean package
java -jar target/finance-api-0.0.1-SNAPSHOT.jar
```

Para parar o banco:

```bash
docker compose down
```

## API HTTP

Base URL:

```text
http://localhost:8080/api/v1
```

Os IDs são UUIDs, por exemplo `550e8400-e29b-41d4-a716-446655440000`.

### Criar usuário

```http
POST /api/v1/users
Content-Type: application/json
```

```json
{
  "fullName": "Maria da Silva",
  "cpf": "12345678900",
  "email": "maria@example.com"
}
```

Resposta atual: `201 Created`, sem corpo. Uma carteira é criada automaticamente com saldo de `1000.00`.

### Listar usuários

```http
GET /api/v1/users
```

Exemplo de resposta:

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "fullName": "Maria da Silva",
    "cpf": "12345678900",
    "email": "maria@example.com"
  }
]
```

### Buscar usuário

```http
GET /api/v1/users/{id}
```

### Buscar carteira

```http
GET /api/v1/wallets/{walletId}
```

Exemplo de resposta:

```json
{
  "id": "7c1e6f4a-0b1c-4c0f-8ca1-7f6d2b7c8a9e",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "balance": 1000.00
}
```

### Transferir valores

```http
POST /api/v1/transactions
Content-Type: application/json
```

```json
{
  "senderId": "7c1e6f4a-0b1c-4c0f-8ca1-7f6d2b7c8a9e",
  "receiverId": "8d2f7a5b-1c2d-4d1f-9db2-8e7e3c8d9f0a",
  "amount": 125.50
}
```

> `senderId` e `receiverId` são os IDs das carteiras, não os IDs dos usuários.

Resposta esperada: `201 Created`, contendo a transação criada:

```json
{
  "id": "9a3e8400-e29b-41d4-a716-446655440111",
  "senderWallet": {
    "id": "7c1e6f4a-0b1c-4c0f-8ca1-7f6d2b7c8a9e",
    "balance": 874.50
  },
  "receiverWallet": {
    "id": "8d2f7a5b-1c2d-4d1f-9db2-8e7e3c8d9f0a",
    "balance": 1125.50
  },
  "amount": 125.50,
  "createdAt": "2026-11-10T19:45:29"
}
```

## Modelo de dados

### `users`

- `id`: UUID da pessoa.
- `name`/`full_name`: nome, conforme a versão do schema/mapeamento.
- `cpf`: CPF único.
- `email`: e-mail único.

### `wallets`

- `id`: UUID da carteira.
- `user_id`: usuário proprietário, com relação um-para-um.
- `balance`: saldo decimal.
- `version`: versão usada pelo optimistic locking.

### `transactions`

- `id`: UUID da transferência.
- `sender_wallet_id`: carteira de origem.
- `receiver_wallet_id`: carteira de destino.
- `amount`: valor transferido.
- `created_at`: momento da criação.

Os UUIDs são declarados como `UUID` em Java e o schema inicial usa `BINARY(16)` no MySQL, reduzindo o espaço de armazenamento em comparação com strings de 36 caracteres. O tipo efetivo deve ser validado na versão do Hibernate/MySQL utilizada no ambiente.

## Regras de negócio

- O saldo inicial de uma carteira nova é `R$ 1.000,00`.
- O valor transferido deve ser maior que zero.
- A carteira de origem precisa existir.
- A carteira de destino precisa existir.
- A carteira de origem precisa ter saldo suficiente.
- Origem e destino não podem ser a mesma carteira.
- CPF e e-mail devem ser únicos no banco.
- O débito e o crédito fazem parte do fluxo transacional da transferência.

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/io/github/danilotavares/financeapi/
│   │   ├── application/
│   │   │   ├── gateway/
│   │   │   └── usecase/
│   │   ├── domain/
│   │   │   └── entity/
│   │   ├── exceptions/
│   │   └── infrastructure/
│   │       ├── controller/
│   │       ├── persistence/
│   │       └── infraestructure/config/
│   └── resources/
│       ├── application.yaml
│       └── db/migration/V1__create_tables.sql
└── test/
    └── java/io/github/danilotavares/financeapi/
```

## Documentação OpenAPI

O projeto inclui Springdoc. Com a aplicação em execução, a interface normalmente fica disponível em:

- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`

## Testes

Execute os testes com:

```bash
./mvnw test
```

O conjunto atual contém um teste de carregamento do contexto Spring. Testes unitários de domínio, casos de uso, integração com banco e concorrência ainda devem ser ampliados.

## Limitações atuais e próximos passos

Antes de usar a aplicação como um serviço financeiro real, os seguintes pontos precisam ser tratados:

- Adicionar validações explícitas nos DTOs (`@NotNull`, `@NotBlank`, `@Email`, validação de CPF e valor positivo).
- Criar tratamento global de erros (`@RestControllerAdvice`) com respostas HTTP consistentes para saldo insuficiente, IDs inexistentes, conflitos e payloads inválidos.
- Corrigir e cobrir com testes o fluxo de crédito na entidade JPA: a implementação atual chama `balance.add(amount)` sem reatribuir o resultado.
- Alinhar a migration Flyway com os nomes usados pelas entidades JPA (`name`/`full_name`, tabela/colunas de transação).
- Confirmar o mapeamento de UUID para `BINARY(16)` na versão efetiva do Hibernate e validar migrations em banco limpo.
- Garantir que o optimistic locking seja exercitado por testes de concorrência e que conflitos sejam traduzidos em resposta adequada.
- Evitar retornar entidades de domínio diretamente no endpoint de transações; criar um DTO de resposta próprio.
- Adicionar autenticação, autorização, idempotência de transferências, auditoria, limites operacionais e observabilidade.
- Não usar `ddl-auto: update` junto com migrations em produção; preferir `validate` após o schema estar controlado pelo Flyway.
- Remover credenciais de desenvolvimento do Compose ou parametrizá-las por ambiente.
- Aumentar a cobertura para persistência, rollback, duplicidade de CPF/e-mail e invariantes de saldo.

## Licença

Nenhuma licença de distribuição foi definida no repositório até o momento.
