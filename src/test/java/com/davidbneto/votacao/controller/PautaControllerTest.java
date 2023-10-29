package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.service.PautaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.davidbneto.votacao.util.RandomHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PautaService pautaService;

    @Test
    @DisplayName("Deve receber a request para criar uma pauta")
    void criarPautRequest() throws Exception {
        var id = gerarLongAleatorio();
        when(pautaService.criarPauta(any())).thenReturn(new PautaCreationResponse(id));
        mockMvc.perform(post("/v1/pauta")
                .contentType(APPLICATION_JSON)
                .content(gerarObjetoAleatorioComoString(PautaCreationRequest.class)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":" + id + "}"));
    }

    @ParameterizedTest
    @DisplayName("Deve receber a request para iniciar uma votação de uma pauta")
    @ValueSource(strings = {"{\"minutos\":10, \"id_pauta\":%d}", "{\"id_pauta\":%d}"})
    void iniciarVotacaoRequest(String body) throws Exception {
        mockMvc.perform(post("/v1/pauta/iniciar")
                .contentType(APPLICATION_JSON)
                .content(body.formatted(gerarLongAleatorio())))
                .andExpect(status().isOk());
    }
}
