# ProjetoFinal — Cadastro de Clientes e Contas Bancárias

Aplicação de console em **Java** que permite cadastrar clientes, associar contas bancárias a cada um e listar tudo em um menu interativo. Projeto feito durante o curso técnico de Análise e Desenvolvimento de Sistemas, com foco em Programação Orientada a Objetos.

## Funcionalidades

1. Cadastrar cliente (nome e CPF)
2. Cadastrar conta bancária (número e saldo inicial) e associá-la a um cliente
3. Mostrar clientes e suas contas
4. Sair

## Estrutura

```
src/com/mycompany/xablau/
├── Main.java            # menu e fluxo do programa
├── Cliente.java         # nome, CPF e lista de contas
└── ContaBancaria.java   # número e saldo
```

## Conceitos praticados

- Classes e objetos, associação entre classes (um cliente tem várias contas)
- Coleções com `ArrayList`
- Entrada de dados com `Scanner`
- Menu em laço com `switch`

## Como executar

Requisito: JDK 17 ou superior.

```bash
git clone https://github.com/Gabrielmachado01gg/ProjetoFinal.git
cd ProjetoFinal
javac -d out src/com/mycompany/xablau/*.java
java -cp out com.mycompany.xablau.Main
```

## Próximos passos

- [ ] Validar o CPF e a entrada de dados
- [ ] Adicionar depósito, saque e transferência
- [ ] Salvar os dados em arquivo ou banco de dados
- [ ] Criar testes automatizados

## Autor

Gabriel Wiedenhoft — [LinkedIn](https://www.linkedin.com/in/gabriel-wiedenhoft-machado)
