# Projeto quarkus-estante-db

## Projeto
O objetivo desta POC é exemplificar uma aplicação que expõe endpoints REST para a execução de CRUD utilizando Quarkus.

A POC simula um cadastro de livros em uma coleção utilizando entidades como Book (livro), Artist (artista), ArtistRole (função do artista) e Publisher (editora) para descrever as principais características de um livro. 

## Tecnologias

- ### Quarkus
Quarkus é um framework Java nativo em Kubernetes e de stack completo que foi desenvolvido para máquinas virtuais Java (JVMs) e compilação nativa.

- ### Postgres
Banco de dados relacional

- ### Panache
Implementação do JPA para Quarkus

- ### JUnit
Framework de testes

- ### MDC
Framework que injeto informações para serem logadas em qualquer momento da aplicação

- ### Docker
Utilizado para subir um container com Postgres

## Executando a aplicação

Para executar a aplicação é necessario excutar um container docker para subir o banco de dados Postgres com o comando abaixo
```shell script
docker container run -d --name postgres-estante -p 5432:5432 -e POSTGRES_PASSWORD=estante_password -e POSTGRES_USER=estante -e POSTGRES_DB=estante postgres
```
Pode utilizar qualquer aplicação de acesso a banco de dados para testar a sua conexão, como o DBeaver.

No projeto executar o comando `./mvnw compile quarkus:dev` para iniciar o projeto

##Usando a aplicação

Para testar a aplicação existe uma collection do Postman no projeto que pode ser utilizada. Nela tem chamadas para todos os endpoints da aplicãção