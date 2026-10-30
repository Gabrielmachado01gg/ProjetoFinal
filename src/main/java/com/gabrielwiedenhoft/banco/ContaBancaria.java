package com.gabrielwiedenhoft.banco;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Conta bancária com saldo em {@link BigDecimal} (nunca use double para dinheiro). */
public class ContaBancaria {

    private final String numero;
    private BigDecimal saldo;

    public ContaBancaria(String numero, BigDecimal saldoInicial) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número da conta é obrigatório.");
        }
        if (saldoInicial == null || saldoInicial.signum() < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }
        this.numero = numero;
        this.saldo = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void depositar(BigDecimal valor) {
        validarValor(valor);
        saldo = saldo.add(valor.setScale(2, RoundingMode.HALF_EVEN));
    }

    public void sacar(BigDecimal valor) {
        validarValor(valor);
        BigDecimal valorArredondado = valor.setScale(2, RoundingMode.HALF_EVEN);
        if (saldo.compareTo(valorArredondado) < 0) {
            throw new SaldoInsuficienteException(numero, saldo, valorArredondado);
        }
        saldo = saldo.subtract(valorArredondado);
    }

    public void transferirPara(ContaBancaria destino, BigDecimal valor) {
        Objects.requireNonNull(destino, "A conta de destino é obrigatória.");
        if (destino == this) {
            throw new IllegalArgumentException("A conta de destino deve ser diferente da origem.");
        }
        sacar(valor);
        destino.depositar(valor);
    }

    private static void validarValor(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
    }

    @Override
    public String toString() {
        return "Conta %s - Saldo: R$ %s".formatted(numero, saldo);
    }
}
