# search.it

Esta é uma aplicação desenvolvida para resolver o Desafio Técnico Luiza Labs da tribo do time de busca. A aplicação é escrita em Java e pode ser executada a partir da linha de comando.

## Pré-requisitos

- **GraalVM JDK 21 ou superior**: Necessário para compilar e executar aplicações Java com desempenho otimizado.
- **Maven 3.8 ou superior**: Utilizado para gerenciar dependências e construir o projeto.

## Instalação do GraalVM

### Usando SDKMAN

1. **Instale o SDKMAN** (se ainda não estiver instalado):

   ```bash
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```

2. **Instale o GraalVM**:

   ```bash
   sdk install java 21.0.3-graal
   ```

3. **Verifique a instalação**:

   ```bash
   java -version
   ```

   O comando deve retornar a versão do GraalVM.

### Configurando o Maven

Após instalar o GraalVM, defina a variável de ambiente `JAVA_HOME` para apontar para a instalação do GraalVM:

```bash
export JAVA_HOME=$(sdk home java 21.0.3-graal)
```

## Configuração do Projeto

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/mateusememe/search.it.git
   cd search.it
   ```

2. **Compile o projeto**:

   ```bash
   mvn clean package -Pnative
   ```

3. **Execute os testes**:

   ```bash
   mvn test
   ```

## Como Executar

1. **Prepare os Arquivos de Filme**: Coloque os arquivos de filmes na pasta `data/`.

2. **Execute a Aplicação**: Use o seguinte comando (dentro de um terminal dentro da pasta do projeto):

   ```bash
   ./target/search.it <termo_de_busca> [-l <limite>] [-v]
   ```

   - `<termo_de_busca>`: Termo que você deseja buscar nos arquivos de filmes (obrigatório).
   - `-l <limite>`: Limite opcional para o número de resultados exibidos (padrão: sem limite).
   - `-v`: Se especificado, a saída será detalhada, incluindo contagem de ocorrências e trechos do conteúdo.

## Estrutura do Projeto

O projeto segue a estrutura padrão do Maven:

```plaintext
search.it/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── mateusememe/
│   │               └── application/
│   │                   └── usecase/
│   │                       └─ SearchMovies.java
│   │               └── domain/
│   │                   └── entity/
│   │                       └─ Indexer.java
│   │               └── infrastructure/
│   │                   └── cli/
│   │                       └─ ArgumentParser.java
│   │                   └── io/
│   │                       └─ MovieFileReader.java
│   │                       └─ OutputPrinter.java
│   │               └── SearchItApplication.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── mateusememe/
│                   └── application/
│                       └── usecase/
│                           └─ SearchMoviesTest.java
│                   └── domain/
│                       └── entity/
│                           └─ IndexerTest.java
│                   └── infrastructure/
│                       └── cli/
│                           └─ ArgumentParserTest.java
│                       └── io/
│                           └─ MovieFileReaderTest.java
│                           └─ OutputPrinterTest.java
│
├── target/
├──── [...]
│──── search.it
└── pom.xml
```

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE.md](./LICENSE) para mais detalhes.

## Contato

Para dúvidas ou sugestões, entre em contato: [matt.mendon@gmail.com](mailto:matt.mendon@gmail.com).
