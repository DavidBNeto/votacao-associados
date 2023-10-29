package com.davidbneto.votacao.service.impl;

import com.davidbneto.votacao.entity.Pauta;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import com.davidbneto.votacao.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.davidbneto.votacao.enumeration.Opcao.SIM;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultadoServiceImpl implements ResultadoService {

    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;

    @Override
    public ResultadoVotacaoResponse obterResultadoVotacao(Long pautaId) {

        log.info("Obtendo resultado da votação da pauta {}", pautaId);

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new NoSuchElementException("Pauta não encontrada com o id: " + pautaId));

        if (pauta.getInicioDaVotacao() == null || pauta.getFimDaVotacao() == null) {
            log.error("A votação da pauta {} ainda não foi iniciada", pautaId);
            throw new IllegalStateException("A votação da pauta " + pautaId + " ainda não foi iniciada");
        }

        if (LocalDateTime.now().isBefore(pauta.getFimDaVotacao())) {
            log.error("A votação da pauta {} ainda está em andamento", pautaId);
            throw new IllegalStateException("A votação da pauta " + pautaId + " ainda está em andamento");
        }

        HashMap<String, Long> resultado = contarVotos(pauta);

        pauta.setVotosSim(resultado.get("SIM"));
        pauta.setVotosNao(resultado.get("NAO"));
        pautaRepository.save(pauta);

        log.info("Resultado da votação da pauta {} obitdo com sucesso", pauta.getId());

        return new ResultadoVotacaoResponse(pauta.getTitulo(), resultado.get("SIM"), resultado.get("NAO"));
    }

    private HashMap<String, Long> contarVotos(Pauta pauta) {
        HashMap<String, Long> resultado = new HashMap<>(Map.of("SIM", 0L, "NAO", 0L));

        votoRepository.findByPautaIdWhereHoraCorreta(pauta.getId(), pauta.getInicioDaVotacao(), pauta.getFimDaVotacao())
                .forEach(voto -> {
                    if (voto.getVoto().equals(SIM)) {
                        resultado.put("SIM", resultado.get("SIM") + 1);
                    } else {
                        resultado.put("NAO", resultado.get("NAO") + 1);
                    }
                });
        return resultado;
    }
}
