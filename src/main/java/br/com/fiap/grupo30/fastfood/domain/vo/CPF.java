package br.com.fiap.grupo30.fastfood.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public record CPF(@NonNull String value) {
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
        int total = 0;
        for (int i = 0; i < cpf.length(); i++) {
            int digit;
            try {
                digit = Integer.parseInt(String.valueOf(cpf.charAt(i)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("CPF contains non-numeric characters");
            }
            if (factor > 1) total += digit * factor--;
        }
        int rest = total % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    private static boolean validate(String cpf) {
        cpf = removeNonDigits(cpf);
        if (isValidLength(cpf) || allDigitsAreTheSame(cpf)) return false;
        int dg1 = calculateDigit(cpf, 10);
        int dg2 = calculateDigit(cpf, 11);
        String actualCheckDigit = cpf.substring(9);
        String calculatedCheckDigit = String.valueOf(dg1) + dg2;
        return actualCheckDigit.equals(calculatedCheckDigit);
    }

    public static boolean isValid(String cpf) {
        return cpf != null && validate(cpf);
    }
}