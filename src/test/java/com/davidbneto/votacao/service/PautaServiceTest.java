package com.davidbneto.votacao.service;

import com.davidbneto.votacao.exception.PautaException;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.service.impl.PautaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.davidbneto.votacao.entity.Pauta;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static com.davidbneto.votacao.util.RandomHelper.gerarObjetoAleatorio;
import static com.davidbneto.votacao.util.RandomHelper.gerarLongAleatorio;
import static com.davidbneto.votacao.util.RandomHelper.gerarStringAleatoria;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    PautaRepository pautaRepository;
    PautaService pautaService;

    @Test
    @DisplayName("Deve criar uma pauta")
    void criarPauta() {
        pautaService = new PautaServiceImpl(pautaRepository);
        Pauta pauta = new Pauta(gerarStringAleatoria());
        pauta.setId(1L);
        Mockito.when(pautaRepository.save(any())).thenReturn(pauta);
        var response = pautaService.criarPauta(new PautaCreationRequest(gerarStringAleatoria()));
        assertEquals(response, new PautaCreationResponse(1L));
    }

    @Test
    @DisplayName("Deve iniciar a votação de uma pauta")
    void iniciarVotacao() {
        pautaService = new PautaServiceImpl(pautaRepository);

        Pauta pauta = new Pauta(gerarStringAleatoria());
        pauta.setId(gerarLongAleatorio());
        var pautaSpy = Mockito.spy(pauta);

        Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pautaSpy));
        PautaVotingRequest request = gerarObjetoAleatorio(PautaVotingRequest.class);

        assertDoesNotThrow( () -> pautaService.iniciarVotacao(request));
        assertNotNull(pautaSpy.getInicioDaVotacao());
        assertNotNull(pautaSpy.getFimDaVotacao());
    }

    @Test
    @DisplayName("Ao tentar iniciar uma votação que não existe, deve lançar uma exceção")
    void erroAoIniciarVotacaoDePautaNaoExistente() {
        pautaService = new PautaServiceImpl(pautaRepository);

        Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());
        PautaVotingRequest request = gerarObjetoAleatorio(PautaVotingRequest.class);

        assertThrows( PautaException.class, () -> pautaService.iniciarVotacao(request));
    }

    @Test
    @DisplayName("Ao tentar iniciar uma votação que já foi iniciada, deve lançar uma exceção")
    void erroAoIniciarVotacaoDePautaCujaVotacaoJaFoiIniciada() {
        pautaService = new PautaServiceImpl(pautaRepository);

        Pauta pauta = new Pauta(gerarStringAleatoria());
        pauta.setId(gerarLongAleatorio());
        pauta.setInicioDaVotacao(LocalDateTime.now());
        var pautaSpy = Mockito.spy(pauta);

        Mockito.when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pautaSpy));
        PautaVotingRequest request = gerarObjetoAleatorio(PautaVotingRequest.class);

        assertThrows( PautaException.class, () -> pautaService.iniciarVotacao(request));
        assertNull(pautaSpy.getFimDaVotacao());
    }
}
