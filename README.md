# Olimpíadas - Com SOLID

## Objetivo
Aplicar o solid sem mexer na estrutura do codigo 

## O que foi mantido
- Fluxo em console
- Cadastro de participante
- Cadastro de prova
- Cadastro de questão
- Aplicação da prova
- Listagem de tentativas
- Dados em memória

## O que foi refatorado
No código original, a classe `App` fazia tudo: menu, entrada de dados, cadastro, aplicação da prova, cálculo de nota e impressão do tabuleiro.

Eu dividi a responsabilidade em poucas classes:

- `App`: inicia o sistema, exibe o menu e chama os serviços
- `CadastroService`: cuida dos cadastros
- `AplicacaoProvaService`: aplica a prova e lista tentativas
- `NotaService`: calcula a nota
- `FenPrinter`: imprime o tabuleiro
- `TabuleiroPrinter`: abstração simples usada pelo serviço

## Onde cada princípio SOLID aparece
### S - Single Responsibility Principle
Cada classe tem uma responsabilidade principal.

### O - Open/Closed Principle
Se o jeito de imprimir o tabuleiro mudar, basta criar outra implementação de `TabuleiroPrinter`.

### L - Liskov Substitution Principle
`AplicacaoProvaService` pode usar qualquer classe que implemente `TabuleiroPrinter` sem quebrar o fluxo.

### I - Interface Segregation Principle
A interface `TabuleiroPrinter` é pequena e focada em uma única necessidade.

### D - Dependency Inversion Principle
`AplicacaoProvaService` depende da abstração `TabuleiroPrinter`, e não diretamente de `FenPrinter`.

## Melhoria funcional incluída
No código original, a prova sempre tentava imprimir a FEN, mas o cadastro da questão não pedia esse dado. Nesta versão, o campo `fenInicial` passou a ser opcional no cadastro e o impressor trata ausência de FEN sem quebrar o sistema.

## Como executar
### Com Maven
```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.ucsal.olimpiadas.App"
```

### Com javac/java
```bash
javac -d out src/main/java/br/com/ucsal/olimpiadas/*.java
java -cp out br.com.ucsal.olimpiadas.App
```

## O motivo de enviar agora
    Eu trabalho no hospital 2 de julho e infelizmente eu alguns profissionais faltaram na quarta feira a noite e por este motivo eu tive que tirar 48h de plantão, saí hoje de manhã e como eu tinha feito a atividade errada, tive que refazer quase toda, acho inclusive que ainda não fiz da maneira certa. Porém de qualquer modo na quinta feira eu gostaria de tirar duvidas com o senhor, se for possível.
