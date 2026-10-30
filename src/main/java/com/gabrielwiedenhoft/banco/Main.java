package com.gabrielwiedenhoft.banco;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Interface de console do sistema bancário. */
public class Main {

    private final Banco banco = new Banco();
    private final Scanner entrada;

    public Main(Scanner entrada) {
        this.entrada = entrada;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(scanner).executar();
        }
    }

    public void executar() {
        boolean continuar = true;
        while (continuar) {
            exibirMenu();
            String opcao = lerTexto("Escolha uma opção: ");
            try {
                continuar = tratarOpcao(opcao);
            } catch (IllegalArgumentException | SaldoInsuficienteException erro) {
                System.out.println("Erro: " + erro.getMessage());
            } catch (NoSuchElementException fimDaEntrada) {
                System.out.println("Entrada encerrada.");
                continuar = false;
            }
            System.out.println();
        }
    }

    private boolean tratarOpcao(String opcao) {
        switch (opcao) {
            case "1" -> cadastrarCliente();
            case "2" -> abrirConta();
            case "3" -> operar(true);
            case "4" -> operar(false);
            case "5" -> transferir();
            case "6" -> listar();
            case "0" -> {
                System.out.println("Saindo do programa.");
                return false;
            }
            default -> System.out.println("Opção inválida.");
        }
        return true;
    }

    private void exibirMenu() {
        System.out.println("=== SISTEMA BANCÁRIO ===");
        System.out.println("1. Cadastrar cliente");
        System.out.println("2. Abrir conta");
        System.out.println("3. Depositar");
        System.out.println("4. Sacar");
        System.out.println("5. Transferir");
        System.out.println("6. Listar clientes e contas");
        System.out.println("0. Sair");
    }

    private void cadastrarCliente() {
        String nome = lerTexto("Nome do cliente: ");
        String cpf = lerTexto("CPF: ");
        Cliente cliente = banco.cadastrarCliente(nome, cpf);
        System.out.println("Cliente cadastrado: " + cliente);
    }

    private void abrirConta() {
        Cliente cliente = banco.buscarCliente(lerTexto("CPF do cliente: "))
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        BigDecimal saldoInicial = lerValor("Saldo inicial: ");
        ContaBancaria conta = banco.abrirConta(cliente, saldoInicial);
        System.out.println("Conta aberta: " + conta);
    }

    private void operar(boolean deposito) {
        ContaBancaria conta = banco.buscarConta(lerTexto("Número da conta: "))
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
        BigDecimal valor = lerValor("Valor: ");
        if (deposito) {
            conta.depositar(valor);
        } else {
            conta.sacar(valor);
        }
        System.out.println("Operação realizada. " + conta);
    }

    private void transferir() {
        String origem = lerTexto("Conta de origem: ");
        String destino = lerTexto("Conta de destino: ");
        BigDecimal valor = lerValor("Valor: ");
        banco.transferir(origem, destino, valor);
        System.out.println("Transferência realizada.");
    }

    private void listar() {
        List<Cliente> clientes = banco.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            if (cliente.getContas().isEmpty()) {
                System.out.println("  (sem contas)");
            }
            cliente.getContas().forEach(conta -> System.out.println("  " + conta));
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private BigDecimal lerValor(String mensagem) {
        String texto = lerTexto(mensagem).replace(",", ".");
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException erro) {
            throw new IllegalArgumentException("Valor numérico inválido: " + texto);
        }
    }
}
