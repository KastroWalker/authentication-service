# Authentication Service

> API para gerênciamento de usuários com autenticação JWT.

## ⚙️ Tecnologias

Tecnologias utilizadas no projeto:

- Kotlin
- Ktor
- Exposed
- Postgres
- Docker

## 💻 Pré-requisitos

Para executar o projeto você precisa ter as seguintes tecnologias instaladas na sua máquina:

- JDK versão 11 ou mais recente
- Docker
- Docker compose

## 🚀 Compilando Authentication Service

Para compilar o Authentication Service, siga estas etapas:

Em uma linha após acessar a pasta raiz do projeto execute:

Linux e macOS:

```
./gradlew shadowjar
```

Windows:

```
gradlew.bat shadowjar
```

## ☕ Executando Authentication Service

Após realizar a compilação do projeto, você pode executar a aplicação com os seguintes comandos:

```
docker-compose build .
docker-compose up
```

## 📝 Licença

Esse projeto está sob licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.

Desenvolvido com :heart: por [Victor Castro](https://twitter.com/kastrowalker).
