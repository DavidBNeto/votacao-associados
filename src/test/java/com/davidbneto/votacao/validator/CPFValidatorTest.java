package com.davidbneto.votacao.validator;

import com.davidbneto.votacao.exception.CPFValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CPFValidatorTest {

    @InjectMocks
    CPFValidator cpfValidator;

    @ParameterizedTest
    @DisplayName("Deve validar com sucesso um cpf com ou sem pontuação")
    @ValueSource(strings = {"490.514.160-59", "27587203028"})
    void deveValidarCpfDeManeiraCorretaComOuSemPontuacao(String cpf) {
        assertDoesNotThrow(() -> cpfValidator.validarCPF(cpf));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar uma excessão se o cpf tiver uma quantidade de caracteres diferente de 11")
    @ValueSource(strings = {"012345678912", "0123456789"})
    void erroAoValidarCpfComQuantidadeErradaDeNumeros(String cpf) {
        assertThrows(CPFValidationException.class, () -> cpfValidator.validarCPF(cpf));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar uma excessão se o cpf tiver todos os caracteres iguais")
    @ValueSource(strings = {"00000000000", "11111111111", "222222222222", "33333333333", "44444444444",
            "55555555555", "66666666666", "77777777777", "88888888888", "99999999999"})
    void erroAoValidarCpfFormadoPorCaracteresIguais(String cpf) {
        assertThrows(CPFValidationException.class, () -> cpfValidator.validarCPF(cpf));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar uma excessão se o cpf tiver problema nos dígitos de verificação")
    @ValueSource(strings = {"987.654.321-02", "123.456.789-19"})
    void erroAoValidarCpfComProblemaNosDigitosDeVerificacao(String cpf) {
        assertThrows(CPFValidationException.class, () -> cpfValidator.validarCPF(cpf));
    }
}
