# 📚 ForumHub API

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de tópicos de fórum, com autenticação via **JWT** e persistência no **MySQL** usando **Flyway** para gerenciamento de migrations.
O projeto segue boas práticas de arquitetura e está configurado para testes via **Insomnia**.

---

## 🚀 Tecnologias Utilizadas

* **Java 21+**
* **Spring Boot**

    * Spring Web
    * Spring Security (JWT)
    * Spring Data JPA
* **MySQL**
* **Maven**
* **Flyway** (migrações de banco)
* **Insomnia** (para testes de API)
* **Swagger OpenAPI** (documentação interativa)

---

## 📦 Pré-requisitos

Antes de começar, você precisa ter instalado em sua máquina:

* [Java JDK 21+](https://www.oracle.com/java/technologies/javase-downloads.html)
* [Maven](https://maven.apache.org/)
* [MySQL](https://dev.mysql.com/downloads/)
* [Insomnia](https://insomnia.rest/download)
* [Git](https://git-scm.com/)

---

## 📥 Clonando o Projeto

```bash
git clone https://github.com/JefersonFreire/challenge-forum-hub.git
cd forumhub
```

---

## ⚙️ Configuração

A aplicação utiliza variáveis de ambiente para configurar o banco de dados e a chave secreta do JWT.

### No Windows (PowerShell)

```powershell
$env:DATASOURCE_URL="jdbc:mysql://localhost:3306/forumhub?useSSL=false&serverTimezone=UTC"
$env:DATASOURCE_USERNAME="root"
$env:DATASOURCE_PASSWORD="sua_senha"
$env:JWT_SECRET="sua_chave_secreta"
```

### No Linux/Mac

```bash
export DATASOURCE_URL="jdbc:mysql://localhost:3306/forumhub?useSSL=false&serverTimezone=UTC"
export DATASOURCE_USERNAME="root"
export DATASOURCE_PASSWORD="sua_senha"
export JWT_SECRET="sua_chave_secreta"
```

---

## 📄 application.properties

```properties
spring.application.name=ForumHub
spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

api.security.token.secret=${JWT_SECRET:123456}
```

---

## ▶️ Executando o Projeto

### Rodar em modo desenvolvimento:

```bash
mvn spring-boot:run
```

### Rodar o .jar gerado:

```bash
mvn clean package
java -jar target/forumhub-0.0.1-SNAPSHOT.jar
```

---

## 📌 Endpoints Principais

### 🔑 Login

**POST** `/login`
Body:

```json
{
  "email" : "juan.lorenzo@dev.com",
  "senha" : "123456"
}
```

Retorna o token JWT para autenticação.

### 👤 Cadastrar Usuário

**POST** `/usuarios/cadastrar`
Body:

```json
{
  "nome": "juan lorenzo",
  "email": "juan.lorenzo@dev.com",
  "senha": "123456"
}
```

### 📝 Criar Tópico

**POST** `/topicos`
Body:

```json
{
  "titulo": "Erro z",
  "mensagem": "Ao rodar o projeto acontece o seguinte erro: erro",
  "curso": {
    "nome": "Java"
  }
}
```

### 📋 Listar Tópicos

**GET** `/topicos`

### 🔍 Detalhar Tópico

**GET** `/topicos/{id}`

Exemplo:

```bash
GET http://localhost:8080/topicos/id
```

### ✏️ Atualizar Tópico

**PUT** `/topicos/{id}`
Body:

```json
{
  "titulo": "string",
  "mensagem": "string",
  "status": "RESOLVIDO",
  "curso": "string"
}
```

### 🗑 Excluir Tópico

**DELETE** `/topicos/{id}`

### 🔓 Logout

Como a autenticação é stateless (JWT), o logout é feito client-side, bastando descartar o token no cliente (ex: Insomnia, front-end, etc.).

---

## 📑 Documentação Swagger

Após rodar a aplicação, acesse:

* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testando no Insomnia

A estrutura de testes está organizada da seguinte forma:

```
📂 Insomnia
 ├── Login
 ├── Usuários
 │   └── Cadastrar Usuário
 ├── Tópicos
 │   ├── Criar Tópico
 │   ├── Listar Tópicos
 │   ├── Detalhar Tópico
 │   ├── Atualizar Tópico
 │   └── Excluir Tópico
```

Passo a passo para testar:

1. Fazer login e copiar o token JWT.
2. Adicionar o token no header `Authorization` no formato:

```makefile
Authorization: Bearer SEU_TOKEN
```

3. Realizar as requisições protegidas.

---

## 🧾 Estrutura de Migrações Flyway

Crie a pasta:

```
src/main/resources/db/migration
```

E dentro dela, crie arquivos SQL seguindo o padrão Flyway, por exemplo:

```
V1__create_perfis_table.sql
V2__create_usuarios_table.sql
V3__create_topicos_table.sql
```

Exemplo `V1__create-tables.sql`:

```sql
create table perfis(
    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key(id)
);

create table usuarios(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(255) not null,
    perfil_id bigint,

    primary key(id),
    constraint fk_usuario_perfil foreign key(perfil_id) references perfis(id)
);

create table cursos(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    categoria varchar(100) not null,

    primary key(id)
);

create table topicos(
    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensagem text not null,
    data_criacao datetime not null,
    status varchar(100) not null,
    autor_id bigint,
    curso_id bigint,

    primary key(id),
    constraint fk_topico_autor foreign key(autor_id) references usuarios(id),
    constraint fk_topico_curso foreign key(curso_id) references cursos(id)
);

create table respostas(
    id bigint not null auto_increment,
    mensagem text not null,
    data_criacao datetime not null,
    topico_id bigint,
    autor_id bigint,
    solucao boolean default false,

    primary key(id),
    constraint fk_resposta_topico foreign key(topico_id) references topicos(id),
    constraint fk_resposta_autor foreign key(autor_id) references usuarios(id)
);

Coloque este arquivo em src/main/resources/db/migration e o Flyway cuidará de criar todas as tabelas automaticamente na primeira execução.


```

---

## 👨‍💻 Autor

**Jeferson Freire**  
Desenvolvedor em transição de carreira com sólida experiência em logística e agora focado em aplicações Java.
