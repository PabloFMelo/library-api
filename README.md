# API REST de gerenciamento de biblioteca, feita para praticar conceitos de programação backend, utilizando Java, SpringBoot.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Swagger (springdoc-openapi)
- Lombok
- Maven
- Postman (para testes)
- JUnit 5 
- Mockito
- Spring Security
- JWT (jjwt)
- BCrypt

## Funcionalidades

- Cadastro, listagem, consulta por id e remoção de **livros**
- Cadastro, listagem, consulta por id e remoção de **membros**, com data de cadastro gerada automaticamente
- Registro de **empréstimos** que relacionam um livro a um membro
- Ao emprestar um livro, o sistema define o prazo de devolução em 7 dias, marca o empréstimo como `ATIVO` e diminui em 1 o número de exemplares disponíveis
- Bloqueio de empréstimo quando o livro não tem exemplares disponíveis
- Devolução de empréstimos: registra a data de devolução, muda o status para `DEVOLVIDO` e devolve o exemplar ao estoque
- Tratamento global de erros: respostas `404` quando um recurso não é encontrado e `400` quando uma regra de negócio é violada, sempre com mensagem clara
- LoanService possui testes unitários das regras de negício.
- O que a API recebe e devolve é separado das entidades do banco.
- Validação com mensagens por campo.
- O sistema marca como ATRASADO os empréstimos com prazo vencido, por uma tarefa agendada de hora em hora.
- Empréstimos atrasados ainda podem ser devolvidos.
- Cadastro e login de usuários, com senha criptografada.
- Rotas protegidas por token.

## Como rodar o projeto

### Pré-requisitos

- JDK 17 ou superior
- Git
- Docker (Docker Desktop)

### Passo a passo

Como usar a API: Fazer POST /auth/register, depois POST /auth/login, e usar o token no botão Authorize do Swagger

1. Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/PabloFMelo/library-api.git
cd library-api
```

2. Execute a aplicação:

```bash
./mvnw spring-boot:run
```
Executar os testes:

```bash
.\mvnw test
```

No Windows, use:

```bash
mvnw.cmd spring-boot:run
```
teste no windows
```bash
.\mvnw.cmd test
```

Documantação interativa:
http://localhost:8080/swagger-ui/index.html

Subir o banco com:
docker compose up -d

(o banco usa a porta 5433, para evitar o mesmo conflito em quem tiver um PostgreSQL instalado.)

3. A API ficará disponível em `http://localhost:8080`.

## Endpoints da API

### Livros

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/books` | Lista todos os livros |
| POST | `/books` | Cadastra um livro |
| GET | `/books/{id}` | Busca um livro pelo id |
| DELETE | `/books/{id}` | Remove um livro |

### Membros

| Método | Rota            | Descrição               |
|--------|-----------------|-------------------------|
| GET | `/members`      | Lista todos os membros  |
| POST | `/members`      | Cadastra um membro      |
| GET | `/members/{id}` | Busca um membro pelo id |
| DELETE | `/members/{id}` | Remove um membro        |


### Empréstimos

| Método |                 Rota                     | Descrição                           |
|--------|------------------------------------------|-------------------------------------|
| GET    | `/loans`                                 | Lista todos os empréstimos          |
| POST   | `/loans?bookId={bookId}&memberId={memberId}` | Cria um empréstimo                  |
| PUT    | `/loans/{id}`                            | Registra a devolução do empréstimo  |


*Exemplo JSON*

```json 
{
  "title": "O Senhor dos Anéis",
  "author": "J.R.R. Tolkien",
  "isbn": "9788533613379",
  "totalCopies": 2,
  "availableCopies": 2
}
```
### Autenticação

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| POST | `/auth/register` | Público | Cadastra um usuário |
| POST | `/auth/login` | Público | Autentica e devolve um token JWT |

Todas as outras rotas exigem o cabeçalho `Authorization: Bearer <token>`. Sem um token válido, a API responde `401`.

**Como autenticar:**

1. Cadastre um usuário em `POST /auth/register`:

```json
{
  "username": "pablo",
  "password": "senha123"
}
```

2. Faça login em `POST /auth/login` com os mesmos dados. A resposta traz o token:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

3. Envie o token nas próximas requisições. No Swagger, clique em **Authorize** e cole o token, sem a palavra `Bearer`.

O token vale por 1 hora. Depois disso, faça login de novo.


