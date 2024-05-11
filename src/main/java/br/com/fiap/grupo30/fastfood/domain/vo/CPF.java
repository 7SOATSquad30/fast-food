package br.com.fiap.grupo30.fastfood.domain.vo;

import lombok.NonNull;

public record CPF(@NonNull String value) {

    private static final int MIN_FACTOR_VALUE = 1;

    public CPF {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid CPF");
        }
    }

    private static boolean isValidLength(String cpf) {
        return cpf.length() != 11;
    }

    private static boolean allDigitsAreTheSame(String cpf) {
        char firstDigit = cpf.charAt(0);
        return cpf.chars().allMatch(c -> c == firstDigit);
    }

    private static String removeNonDigits(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private static int calculateDigit(String cpf, int factor) {
        int num = factor;
        int total = 0;
        for (int i = 0; i < cpf.length(); i++) {
            int digit;
            try {
                digit = Integer.parseInt(String.valueOf(cpf.charAt(i)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("CPF contains non-numeric characters", e);
            }
            if (num > MIN_FACTOR_VALUE) {
                total += digit * num--;
            }
        }
        int rest = total % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    private static boolean validate(String cpf) {
        var cpfNonDigits = removeNonDigits(cpf);
        if (isValidLength(cpfNonDigits) || allDigitsAreTheSame(cpfNonDigits)) return false;
        int dg1 = calculateDigit(cpfNonDigits, 10);
        int dg2 = calculateDigit(cpfNonDigits, 11);
        String actualCheckDigit = cpfNonDigits.substring(9);
        String calculatedCheckDigit = String.valueOf(dg1) + dg2;
        return actualCheckDigit.equals(calculatedCheckDigit);
    }

    public static boolean isValid(String cpf) {
        return cpf != null && validate(cpf);
    }
}
