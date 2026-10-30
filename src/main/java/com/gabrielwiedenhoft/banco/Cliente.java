package com.gabrielwiedenhoft.banco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {

    private final String nome;
    private final Cpf cpf;
    private final List<ContaBancaria> contas = new ArrayList<>();

    public Cliente(String nome, Cpf cpf) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cpf == null) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório.");
        }
        this.nome = nome.trim();
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public Cpf getCpf() {
        return cpf;
    }

    /** Lista somente leitura: para adicionar uma conta, use {@link #adicionarConta}. */
    public List<ContaBancaria> getContas() {
        return Collections.unmodifiableList(contas);
    }

    public void adicionarConta(ContaBancaria conta) {
        contas.add(conta);
    }

    @Override
    public String toString() {
        return "Cliente: %s (CPF: %s)".formatted(nome, cpf);
    }
}
