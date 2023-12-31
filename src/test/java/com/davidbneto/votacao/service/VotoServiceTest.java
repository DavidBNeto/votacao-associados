package com.davidbneto.votacao.service;

import com.davidbneto.votacao.entity.Pauta;
import com.davidbneto.votacao.entity.Voto;
import com.davidbneto.votacao.exception.InvalidVoteException;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.service.impl.VotoServiceImpl;
import com.davidbneto.votacao.validator.CPFValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.davidbneto.votacao.util.RandomHelper.gerarObjetoAleatorio;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    VotoRepository votoRepository;
    @Mock
    CPFValidator cpfValidator;
    @Mock
    PautaRepository pautaRepository;
    VotoService votoService;

    @ParameterizedTest
    @DisplayName("Deve votar com sucesso")
    @ValueSource(strings = {"SIM", "NAO", "NÃO", "sim", "nao", "não"})
    void votar(String voto) {
        votoService = new VotoServiceImpl(votoRepository, pautaRepository, cpfValidator);
        VotoRequest request = gerarObjetoAleatorio(VotoRequest.class);
        when(cpfValidator.validarCPF(any())).thenReturn(request.getCpf());
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(gerarObjetoAleatorio(Pauta.class)));
        request.setVoto(voto);

        when(votoRepository.findByCpf(any())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> votoService.votar(request));
    }

    @Test
    @DisplayName("Deve lançar uma excessão caso a pauta não exista")
    void erroAoVotarEmPautaInexistente() {
        votoService = new VotoServiceImpl(votoRepository, pautaRepository, cpfValidator);
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> votoService.votar(gerarObjetoAleatorio(VotoRequest.class)));
    }

    @Test
    @DisplayName("Deve lançar uma excessão caso o voto seja inválido")
    void erroAoVotarOpcaoInvalida() {
        votoService = new VotoServiceImpl(votoRepository, pautaRepository, cpfValidator);
        when(cpfValidator.validarCPF(any())).thenReturn("12345678901");
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(gerarObjetoAleatorio(Pauta.class)));
        assertThrows(InvalidVoteException.class, () -> votoService.votar(gerarRequestComVotoInvalido()));
    }

    @Test
    @DisplayName("Deve lançar uma excessão caso o cpf já tenha votado")
    void erroAoVotarMaisDeUmaVez() {
        votoService = new VotoServiceImpl(votoRepository, pautaRepository, cpfValidator);
        VotoRequest request = gerarObjetoAleatorio(VotoRequest.class);
        request.setVoto("SIM");
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(gerarObjetoAleatorio(Pauta.class)));
        when(cpfValidator.validarCPF(any())).thenReturn(request.getCpf());
        when(votoRepository.findByCpf(any())).thenReturn(Optional.of(gerarObjetoAleatorio(Voto.class)));

        assertThrows(InvalidVoteException.class, () -> votoService.votar(request));
    }

    private VotoRequest gerarRequestComVotoInvalido() {
        VotoRequest request;
        do {
            request = gerarObjetoAleatorio(VotoRequest.class);
        } while (request.getVoto().equals("SIM") || request.getVoto().equals("NAO") ||
                request.getVoto().equals("sim") || request.getVoto().equals("nao"));
        return request;
    }
}
