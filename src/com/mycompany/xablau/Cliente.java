package com.mycompany.xablau;

import java.util.ArrayList;

public class Cliente {
    String nome;
    String cpf;
    ArrayList<ContaBancaria> contas;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(ContaBancaria conta) {
        this.contas.add(conta);
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " (CPF: " + cpf + ")";
    }
}
