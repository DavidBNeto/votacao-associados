package com.davidbneto.votacao.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.LongRangeRandomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import lombok.NoArgsConstructor;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RandomHelper {

    private static final EnhancedRandomBuilder random = aNewEnhancedRandomBuilder();
    private static final Long MIN_LONG = 0L;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String gerarStringAleatoria() {
        return random(String.class, new StringRandomizer());
    }

    public static <T> T gerarObjetoAleatorio(Class<T> clazz) {
        return aNewEnhancedRandomBuilder().build().nextObject(clazz);
    }

    public static String gerarObjetoAleatorioComoString(Class<?> clazz) throws Exception {
        return objectMapper.writeValueAsString(gerarObjetoAleatorio(clazz)).toString();
    }

    public static Long gerarLongAleatorio(Long max) {
        return random(Long.class, new LongRangeRandomizer(MIN_LONG, max));
    }

    public static Long gerarLongAleatorio() {
        return gerarLongAleatorio(Long.MAX_VALUE);
    }

    private static <T> T random(Class<T> clazz, Randomizer<T> randomizer) {
        return random.randomize(clazz, randomizer).build().nextObject(clazz);
    }
}
