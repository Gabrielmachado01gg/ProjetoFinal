# ProjetoFinal

package com.mycompany.xablau;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Cadastrar conta bancária");
            System.out.println("3. Mostrar clientes e suas contas");
            System.out.println("4. Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolha) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    cadastrarConta(scanner);
                    break;
                case 3:
                    mostrarClientesEContas();
                    break;
                case 4:
                    System.out.println("Saindo do programa.");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public static void cadastrarCliente(Scanner scanner) {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf);
        clientes.add(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void cadastrarConta(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados. Cadastre um cliente primeiro.");
            return;
        }

        System.out.println("Selecione um cliente para associar a conta:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).nome);
        }

        int escolha = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        Cliente cliente = clientes.get(escolha - 1);

        System.out.println("Digite o número da conta:");
        String numeroConta = scanner.nextLine();

        System.out.println("Digite o saldo inicial da conta:");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        ContaBancaria conta = new ContaBancaria(numeroConta, saldoInicial);
        cliente.adicionarConta(conta);

        System.out.println("Conta cadastrada com sucesso!");
    }

    public static void mostrarClientesEContas() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println("Contas:");
            for (ContaBancaria conta : cliente.contas) {
                System.out.println(conta);
            }
            System.out.println();
        }
    }
}
