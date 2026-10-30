package com.gabrielwiedenhoft.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoTest {

    private static final String CPF_ANA = "529.982.247-25";
    private static final String CPF_BRUNO = "111.444.777-35";

    private Banco banco;

    @BeforeEach
    void criarBanco() {
        banco = new Banco();
    }

    @Test
    void cadastraEBuscaClientePorCpf() {
        banco.cadastrarCliente("Ana", CPF_ANA);
        assertTrue(banco.buscarCliente("52998224725").isPresent());
    }

    @Test
    void naoPermiteDoisClientesComOMesmoCpf() {
        banco.cadastrarCliente("Ana", CPF_ANA);
        assertThrows(IllegalArgumentException.class, () -> banco.cadastrarCliente("Outra Ana", "52998224725"));
    }

    @Test
    void numerosDeContaSaoSequenciais() {
        Cliente ana = banco.cadastrarCliente("Ana", CPF_ANA);
        assertEquals("0001", banco.abrirConta(ana, BigDecimal.ZERO).getNumero());
        assertEquals("0002", banco.abrirConta(ana, BigDecimal.ZERO).getNumero());
        assertEquals(2, ana.getContas().size());
    }

    @Test
    void transfereEntreContasDeClientesDiferentes() {
        Cliente ana = banco.cadastrarCliente("Ana", CPF_ANA);
        Cliente bruno = banco.cadastrarCliente("Bruno", CPF_BRUNO);
        ContaBancaria contaAna = banco.abrirConta(ana, new BigDecimal("200"));
        ContaBancaria contaBruno = banco.abrirConta(bruno, BigDecimal.ZERO);

        banco.transferir(contaAna.getNumero(), contaBruno.getNumero(), new BigDecimal("75.50"));

        assertEquals(new BigDecimal("124.50"), contaAna.getSaldo());
        assertEquals(new BigDecimal("75.50"), contaBruno.getSaldo());
    }

    @Test
    void transferenciaComContaInexistenteLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> banco.transferir("9999", "8888", BigDecimal.TEN));
    }

    @Test
    void listaDeClientesNaoPodeSerAlteradaPorFora() {
        banco.cadastrarCliente("Ana", CPF_ANA);
        assertThrows(UnsupportedOperationException.class, () -> banco.listarClientes().clear());
    }
}
