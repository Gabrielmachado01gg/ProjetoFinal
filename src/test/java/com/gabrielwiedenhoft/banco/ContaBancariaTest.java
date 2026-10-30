package com.gabrielwiedenhoft.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ContaBancariaTest {

    private static BigDecimal reais(String valor) {
        return new BigDecimal(valor);
    }

    @Test
    void depositoAumentaOSaldo() {
        ContaBancaria conta = new ContaBancaria("0001", reais("100"));
        conta.depositar(reais("50.25"));
        assertEquals(reais("150.25"), conta.getSaldo());
    }

    @Test
    void saqueDiminuiOSaldo() {
        ContaBancaria conta = new ContaBancaria("0001", reais("100"));
        conta.sacar(reais("40"));
        assertEquals(reais("60.00"), conta.getSaldo());
    }

    @Test
    void saqueMaiorQueOSaldoLancaExcecaoEMantemSaldo() {
        ContaBancaria conta = new ContaBancaria("0001", reais("10"));
        assertThrows(SaldoInsuficienteException.class, () -> conta.sacar(reais("10.01")));
        assertEquals(reais("10.00"), conta.getSaldo());
    }

    @Test
    void valoresZeradosOuNegativosSaoRejeitados() {
        ContaBancaria conta = new ContaBancaria("0001", reais("10"));
        assertThrows(IllegalArgumentException.class, () -> conta.depositar(reais("0")));
        assertThrows(IllegalArgumentException.class, () -> conta.sacar(reais("-5")));
    }

    @Test
    void saldoInicialNegativoERejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new ContaBancaria("0001", reais("-1")));
    }

    @Test
    void transferenciaMoveOValorEntreContas() {
        ContaBancaria origem = new ContaBancaria("0001", reais("100"));
        ContaBancaria destino = new ContaBancaria("0002", reais("0"));
        origem.transferirPara(destino, reais("30"));
        assertEquals(reais("70.00"), origem.getSaldo());
        assertEquals(reais("30.00"), destino.getSaldo());
    }

    @Test
    void transferenciaParaAMesmaContaERejeitada() {
        ContaBancaria conta = new ContaBancaria("0001", reais("100"));
        assertThrows(IllegalArgumentException.class, () -> conta.transferirPara(conta, reais("10")));
    }

    @Test
    void transferenciaSemSaldoNaoAlteraNenhumaConta() {
        ContaBancaria origem = new ContaBancaria("0001", reais("5"));
        ContaBancaria destino = new ContaBancaria("0002", reais("0"));
        assertThrows(SaldoInsuficienteException.class, () -> origem.transferirPara(destino, reais("10")));
        assertEquals(reais("5.00"), origem.getSaldo());
        assertEquals(reais("0.00"), destino.getSaldo());
    }
}
