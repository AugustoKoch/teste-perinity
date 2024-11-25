# Teste BackEnd Perinity

Este é um projeto backend desenvolvido com **Spring Boot** para o gerenciamento de pessoas, tarefas e departamentos. Ele foi feito para teste para a vaga de desenvolvedor backend da Perinity.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Junit 5** e **Mockito**
- **Swagger**
- **Spring Data JPA**

## Pré-requisitos

Antes de rodar o projeto, você precisa ter as seguintes ferramentas instaladas:

- **Java 21** (JDK 21)
- **PostgreSQL** (ou outro banco de dados de sua escolha, configurando o arquivo `application.properties` adequadamente)
- **Maven** (para gerenciamento de dependências e execução do projeto)

## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu_usuario/testeperinity.git

2. Navegue para o diretório do projeto

   ```bash
   cd teste-perinity

3. Configure o banco de dados:

- Instale o PostgreSQL, se ainda não o tiver.
- Crie um banco de dados com o nome de sua escolha.
- No arquivo `src/main/resources/application.properties`, configure a conexão com o banco de dados:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

4. Execute o comando Maven para rodar o projeto:

  ```bash
  mvn spring-boot:run
  ```

## Swagger - Documentação da API
Este projeto integra o Swagger para gerar e visualizar a documentação interativa da API.

**Como acessar o Swagger:**
Ao rodar o projeto, você pode acessar a interface do Swagger para interagir com os endpoints da API. A interface do Swagger estará disponível na seguinte URL:

  ```bash
  http://localhost:8080/swagger-ui.html
  ```

**Funcionalidades do Swagger:**
- Visualização dos Endpoints: O Swagger exibe todos os endpoints da API, com métodos HTTP (GET, POST, PUT, DELETE), parâmetros e respostas esperadas.
- Testar Endpoints: Você pode testar as rotas diretamente da interface do Swagger, fornecendo parâmetros e visualizando as respostas da API em tempo real.
