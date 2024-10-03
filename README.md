# search.it
Esta é uma aplicação que tem como intuito resolver o Desafio Técnico Luiza Labs da tribo do time de busca.
A aplicação foi desenvolvida em Java e é executável por meio de linha de comando

## Pré-requisitos

- Java JDK 15 ou superior
- Maven 3.8 ou superior

## Configuração do Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/mateusememe/search.it.git
   cd search.it
   ```

2. Compile o projeto:
   ```bash
   mvn compile
   ```

3. Execute os testes:
   ```bash
   mvn test
   ```

4. Crie um arquivo JAR executável:
   ```bash
   mvn package
   ```

## Execução

Para executar a aplicação, use o seguinte comando:

```bash
java -jar target/search.it-1.0-SNAPSHOT.jar [argumentos]
```

Exemplo:
```bash
java -jar target/search.it-1.0-SNAPSHOT.jar arg1 arg2 arg3
```

## Estrutura do Projeto

O projeto segue a estrutura padrão do Maven:

```bash
search.it/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── mateusememe/
│   │               └── SearchIt.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── mateusememe/
│                   └── SearchItTest.java
│
├── target/
│
└── pom.xml
```

## Desenvolvimento

1. A classe principal está localizada em `src/main/java/com/mateusememe/App.java`.
2. Os testes unitários estão localizados em `src/test/java/`.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.
