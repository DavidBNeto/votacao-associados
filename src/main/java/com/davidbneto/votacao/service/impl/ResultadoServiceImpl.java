package com.davidbneto.votacao.service.impl;

import com.davidbneto.votacao.entity.Pauta;
import com.davidbneto.votacao.messaging.Produtor;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import com.davidbneto.votacao.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Value("${spring.rabbitmq.exchanges.resultados.name}")
    private String exchangeDeVotacao;
    @Value("${spring.rabbitmq.exchanges.resultados.key}")
    private String keyVotacao;
    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final StringRedisTemplate cache;
    private final Produtor produtor;

    @Override
    public ResultadoVotacaoResponse obterResultadoVotacao(Long pautaId) {

        log.info("Obtendo resultado da votação da pauta {}", pautaId);

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new NoSuchElementException("Pauta não encontrada com o id: " + pautaId));

        validarHoraDaPauta(pauta);

        contarVotos(pauta);

        log.info("Resultado da votação da pauta {} obitdo com sucesso", pauta.getId());

        ResultadoVotacaoResponse resposta = new ResultadoVotacaoResponse(pauta.getTitulo(), pauta.getVotosSim(), pauta.getVotosNao());

        removerVotosEPautaDoCache(pautaId);

        produtor.enviarObjeto(resposta, exchangeDeVotacao, keyVotacao);

        return resposta;
    }

    private static void validarHoraDaPauta(final Pauta pauta) {
        final Long pautaId = pauta.getId();

        if (pauta.getInicioDaVotacao() == null || pauta.getFimDaVotacao() == null) {
            log.error("A votação da pauta {} ainda não foi iniciada", pautaId);
            throw new IllegalStateException("A votação da pauta " + pautaId + " ainda não foi iniciada");
        }

        if (LocalDateTime.now().isBefore(pauta.getFimDaVotacao())) {
            log.error("A votação da pauta {} ainda está em andamento", pautaId);
            throw new IllegalStateException("A votação da pauta " + pautaId + " ainda está em andamento");
        }
    }

    private void removerVotosEPautaDoCache(final Long pautaId) {

        cache.opsForValue().getAndDelete(pautaId + "");

        for (String key : cache.keys( pautaId + "-*")) {
            cache.opsForValue().getAndDelete(key);
        }
    }

    private void contarVotos(Pauta pauta) {
        HashMap<String, Long> resultado = new HashMap<>(Map.of("SIM", 0L, "NAO", 0L));

        votoRepository.findByPautaIdWhereHoraCorreta(pauta.getId(), pauta.getInicioDaVotacao(), pauta.getFimDaVotacao())
                .forEach(voto -> {
                    if (voto.getVoto().equals(SIM)) {
                        resultado.put("SIM", resultado.get("SIM") + 1);
                    } else {
                        resultado.put("NAO", resultado.get("NAO") + 1);
                    }
                });

        pauta.setVotosSim(resultado.get("SIM"));
        pauta.setVotosNao(resultado.get("NAO"));
        pautaRepository.save(pauta);
    }
}
