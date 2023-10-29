package com.davidbneto.votacao.validator;

import com.davidbneto.votacao.exception.CPFValidationException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class CPFValidator {
    
    public String validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpfInvalido(cpf)) {
            throw new CPFValidationException(cpf);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (10 - i);
        }
        int firstVerificationDigit = 11 - (sum % 11);

        if (firstVerificationDigit >= 10) {
            firstVerificationDigit = 0;
        }

        if (firstVerificationDigit != Character.getNumericValue(cpf.charAt(9))) {
            throw new CPFValidationException(cpf);
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (11 - i);
        }
        int secondVerificationDigit = 11 - (sum % 11);

        if (secondVerificationDigit >= 10) {
            secondVerificationDigit = 0;
        }

        if (!(secondVerificationDigit == Character.getNumericValue(cpf.charAt(10)))) {
            throw new CPFValidationException(cpf);
        }

        return cpf;
    }

    private static boolean cpfInvalido(String cpf) {
        return cpf.length() != 11 || cpf.equals("00000000000") || cpf.equals("11111111111")
                || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777")
                || cpf.equals("88888888888") || cpf.equals("99999999999");
    }

}
