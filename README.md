# API REST de gerenciamento de biblioteca, feita para praticar conceitos de programação backend, utilizando Java, SpringBoot.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- Postman (para testes)

## Funcionalidades

- Cadastro, listagem, consulta por id e remoção de **livros**
- Cadastro, listagem, consulta por id e remoção de **membros**, com data de cadastro gerada automaticamente
- Registro de **empréstimos** que relacionam um livro a um membro
- Ao emprestar um livro, o sistema define o prazo de devolução em 7 dias, marca o empréstimo como `ATIVO` e diminui em 1 o número de exemplares disponíveis
- Bloqueio de empréstimo quando o livro não tem exemplares disponíveis
- Devolução de empréstimos: registra a data de devolução, muda o status para `DEVOLVIDO` e devolve o exemplar ao estoque
- Tratamento global de erros: respostas `404` quando um recurso não é encontrado e `400` quando uma regra de negócio é violada, sempre com mensagem clara

## Como rodar o projeto

### Pré-requisitos

- JDK 17 ou superior
- Git

### Passo a passo

1. Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/PabloFMelo/library-api.git
cd library-api
```

2. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows, use:

```bash
mvnw.cmd spring-boot:run
```

3. A API ficará disponível em `http://localhost:8080`.

> **Observação:** o projeto usa o banco H2 em memória, então todos os dados são apagados sempre que a aplicação é reiniciada.


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


