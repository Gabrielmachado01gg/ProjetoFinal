package com.gabrielwiedenhoft.banco;

import java.math.BigDecimal;

/** Lançada quando uma operação pede mais dinheiro do que a conta possui. */
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String numeroConta, BigDecimal saldo, BigDecimal valor) {
        super("Saldo insuficiente na conta %s: saldo R$ %s, valor solicitado R$ %s"
                .formatted(numeroConta, saldo, valor));
    }
}
