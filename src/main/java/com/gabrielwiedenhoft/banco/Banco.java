package com.gabrielwiedenhoft.banco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Regras de negócio do banco. Guarda os dados em memória e não faz entrada nem saída de tela. */
public class Banco {

    private final Map<Cpf, Cliente> clientes = new LinkedHashMap<>();
    private final Map<String, ContaBancaria> contasPorNumero = new LinkedHashMap<>();
    private int proximoNumeroConta = 1;

    public Cliente cadastrarCliente(String nome, String cpfTexto) {
        Cpf cpf = Cpf.of(cpfTexto);
        if (clientes.containsKey(cpf)) {
            throw new IllegalArgumentException("Já existe um cliente com o CPF " + cpf + ".");
        }
        Cliente cliente = new Cliente(nome, cpf);
        clientes.put(cpf, cliente);
        return cliente;
    }

    public Optional<Cliente> buscarCliente(String cpfTexto) {
        return Optional.ofNullable(clientes.get(Cpf.of(cpfTexto)));
    }

    /** Abre uma conta com número gerado automaticamente (0001, 0002, ...). */
    public ContaBancaria abrirConta(Cliente cliente, BigDecimal saldoInicial) {
        String numero = "%04d".formatted(proximoNumeroConta++);
        ContaBancaria conta = new ContaBancaria(numero, saldoInicial);
        cliente.adicionarConta(conta);
        contasPorNumero.put(numero, conta);
        return conta;
    }

    public Optional<ContaBancaria> buscarConta(String numero) {
        return Optional.ofNullable(contasPorNumero.get(numero));
    }

    public void transferir(String numeroOrigem, String numeroDestino, BigDecimal valor) {
        ContaBancaria origem = buscarConta(numeroOrigem)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada: " + numeroOrigem));
        ContaBancaria destino = buscarConta(numeroDestino)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada: " + numeroDestino));
        origem.transferirPara(destino, valor);
    }

    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(new ArrayList<>(clientes.values()));
    }
}
