package com.gabrielwiedenhoft.banco;

import java.util.Objects;

/**
 * Objeto de valor que representa um CPF válido.
 * Aceita a entrada com ou sem pontuação e valida os dígitos verificadores.
 */
public final class Cpf {

    private final String digitos;

    private Cpf(String digitos) {
        this.digitos = digitos;
    }

    /**
     * Cria um CPF a partir de um texto.
     *
     * @throws IllegalArgumentException se o texto não for um CPF válido
     */
    public static Cpf of(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo.");
        }
        String apenasDigitos = texto.replaceAll("\\D", "");
        if (!ehValido(apenasDigitos)) {
            throw new IllegalArgumentException("CPF inválido: " + texto);
        }
        return new Cpf(apenasDigitos);
    }

    public static boolean ehValido(String apenasDigitos) {
        if (apenasDigitos == null || apenasDigitos.length() != 11) {
            return false;
        }
        // Rejeita sequências como 111.111.111-11, que passam no cálculo mas não são válidas
        if (apenasDigitos.chars().distinct().count() == 1) {
            return false;
        }
        int primeiro = calcularDigito(apenasDigitos, 9);
        int segundo = calcularDigito(apenasDigitos, 10);
        return primeiro == Character.getNumericValue(apenasDigitos.charAt(9))
                && segundo == Character.getNumericValue(apenasDigitos.charAt(10));
    }

    private static int calcularDigito(String digitos, int quantidade) {
        int soma = 0;
        for (int i = 0; i < quantidade; i++) {
            soma += Character.getNumericValue(digitos.charAt(i)) * (quantidade + 1 - i);
        }
        int resto = (soma * 10) % 11;
        return resto == 10 ? 0 : resto;
    }

    /** Retorna o CPF no formato 000.000.000-00. */
    public String formatado() {
        return digitos.substring(0, 3) + "." + digitos.substring(3, 6) + "."
                + digitos.substring(6, 9) + "-" + digitos.substring(9);
    }

    public String digitos() {
        return digitos;
    }

    @Override
    public boolean equals(Object outro) {
        return outro instanceof Cpf cpf && digitos.equals(cpf.digitos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitos);
    }

    @Override
    public String toString() {
        return formatado();
    }
}
