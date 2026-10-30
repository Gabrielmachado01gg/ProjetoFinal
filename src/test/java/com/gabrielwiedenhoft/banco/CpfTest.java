package com.gabrielwiedenhoft.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CpfTest {

    @Test
    void aceitaCpfValidoComOuSemPontuacao() {
        assertEquals(Cpf.of("529.982.247-25"), Cpf.of("52998224725"));
    }

    @Test
    void formataNoPadraoBrasileiro() {
        assertEquals("529.982.247-25", Cpf.of("52998224725").formatado());
    }

    @ParameterizedTest
    @ValueSource(strings = {"529.982.247-24", "123", "", "abc", "111.111.111-11", "000.000.000-00"})
    void rejeitaCpfInvalido(String texto) {
        assertThrows(IllegalArgumentException.class, () -> Cpf.of(texto));
    }

    @Test
    void rejeitaCpfNulo() {
        assertThrows(IllegalArgumentException.class, () -> Cpf.of(null));
    }

    @Test
    void ehValidoReconheceDigitosVerificadores() {
        assertTrue(Cpf.ehValido("11144477735"));
        assertFalse(Cpf.ehValido("11144477736"));
    }
}
