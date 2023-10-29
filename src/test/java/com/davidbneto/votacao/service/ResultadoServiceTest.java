package com.davidbneto.votacao.service;

import com.davidbneto.votacao.entity.Pauta;
import com.davidbneto.votacao.entity.Voto;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.service.impl.ResultadoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static com.davidbneto.votacao.util.RandomHelper.gerarLongAleatorio;
import static com.davidbneto.votacao.util.RandomHelper.gerarObjetoAleatorio;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultadoServiceTest {

    @Mock
    PautaRepository pautaRepository;
    @Mock
    VotoRepository votoRepository;
    ResultadoService resultadoService;

    @Test
    @DisplayName("Deve obter o resultado da votação de uma pauta com sucesso")
    void obterResultadoVotacao() {
        resultadoService = new ResultadoServiceImpl(votoRepository, pautaRepository);
        Pauta pauta = gerarObjetoAleatorio(Pauta.class);
        long qVotos = gerarLongAleatorio(10L);

        when(votoRepository.findByPautaIdWhereHoraCorreta(anyLong(), any(), any()))
                .thenReturn(gerarNVotos(qVotos, pauta.getId()));
        pauta.setFimDaVotacao(LocalDateTime.now());
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
        var result = assertDoesNotThrow(() -> resultadoService.obterResultadoVotacao(gerarLongAleatorio()));
        assertEquals(pauta.getTitulo(), result.titulo());
        assertEquals(qVotos, result.votosNao() + result.votosSim());
    }

    @Test
    @DisplayName("Deve lançar uma excessão se o id da pauta não existir")
    void erroAoSolicitarResultadoDePautaNaoExistente() {
        resultadoService = new ResultadoServiceImpl(votoRepository, pautaRepository);
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> resultadoService.obterResultadoVotacao(gerarLongAleatorio()));
    }

    @Test
    @DisplayName("Deve lançar uma excessão se a votação não tiver iniciado")
    void erroAoSolicitarResultadoDePautaCujaVotacaoNaoIniciou() {
        resultadoService = new ResultadoServiceImpl(votoRepository, pautaRepository);
        Pauta pauta = gerarObjetoAleatorio(Pauta.class);
        pauta.setFimDaVotacao(null);
        pauta.setInicioDaVotacao(null);
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
        assertThrows(IllegalStateException.class, () -> resultadoService.obterResultadoVotacao(gerarLongAleatorio()));
    }

    @Test
    @DisplayName("Deve lançar uma excessão se a votação não tiver terminado")
    void erroAoSolicitarResultadoDePautaCujaVotacaoNaoTerminou() {
        resultadoService = new ResultadoServiceImpl(votoRepository, pautaRepository);
        Pauta pauta = gerarObjetoAleatorio(Pauta.class);
        pauta.setFimDaVotacao(LocalDateTime.now().plusHours(7L));
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
        assertThrows(IllegalStateException.class, () -> resultadoService.obterResultadoVotacao(gerarLongAleatorio()));
    }

    private List<Voto> gerarNVotos(Long n, Long pauta) {
        List<Voto> votos = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            Voto voto = gerarObjetoAleatorio(Voto.class);
            voto.setPauta(pauta);
            voto.setHora(LocalDateTime.now());
            votos.add(voto);
        }
        return votos;
    }
}
