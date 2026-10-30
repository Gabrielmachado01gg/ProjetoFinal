package com.mycompany.xablau;

public class ContaBancaria {
    String numero;
    double saldo;

    public ContaBancaria(String numero, double saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta: " + numero + " - Saldo: R$" + String.format("%.2f", saldo);
    }
}
