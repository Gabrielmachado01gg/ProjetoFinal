# Sistema Bancário — Java

[![CI](https://github.com/Gabrielmachado01gg/ProjetoFinal/actions/workflows/ci.yml/badge.svg)](https://github.com/Gabrielmachado01gg/ProjetoFinal/actions/workflows/ci.yml)
![Java 17](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Licença MIT](https://img.shields.io/badge/licen%C3%A7a-MIT-green)

Sistema bancário de console em **Java 17**, com regras de negócio separadas da interface e cobertura de testes automatizados. Começou como trabalho do curso técnico de Análise e Desenvolvimento de Sistemas e foi refatorado seguindo boas práticas de mercado.

## Funcionalidades

- Cadastro de clientes com **validação de CPF** (dígitos verificadores)
- Abertura de contas com número gerado automaticamente
- Depósito, saque e **transferência** entre contas
- Listagem de clientes e contas

## Decisões de projeto

- **`BigDecimal` para dinheiro:** `double` acumula erro de arredondamento.
- **Encapsulamento:** atributos privados, lista de contas somente leitura, objetos válidos desde a criação.
- **Objeto de valor `Cpf`:** a validação fica em um só lugar e um CPF inválido nunca entra no sistema.
- **Separação de responsabilidades:** `Banco` guarda as regras e `Main` só cuida da tela.
- **Exceções específicas:** `SaldoInsuficienteException` em vez de mensagens soltas.
- **Operações atômicas:** uma transferência que falha não altera nenhuma das contas.

## Estrutura

```
src/main/java/com/gabrielwiedenhoft/banco/
├── Main.java                       # interface de console
├── Banco.java                      # regras de negócio
├── Cliente.java
├── ContaBancaria.java
├── Cpf.java                        # objeto de valor com validação
└── SaldoInsuficienteException.java
src/test/java/...                   # testes JUnit 5
```

## Como executar

Requisitos: JDK 17+ e Maven 3.9+.

```bash
git clone https://github.com/Gabrielmachado01gg/ProjetoFinal.git
cd ProjetoFinal
mvn -B test                 # roda os testes
mvn -q package -DskipTests  # gera o jar
java -jar target/sistema-bancario-1.0.0.jar
```

## Testes

Os testes cobrem a validação de CPF, depósito, saque, transferência (incluindo falhas) e as regras do `Banco`. Rodam automaticamente no GitHub Actions a cada push.

## Próximos passos

- [ ] Persistência em banco de dados (JDBC ou JPA)
- [ ] Extrato com histórico de operações
- [ ] API REST com Spring Boot

## Autor

Gabriel Wiedenhoft — [LinkedIn](https://www.linkedin.com/in/gabriel-wiedenhoft-8a3256286)

Licença [MIT](LICENSE).
