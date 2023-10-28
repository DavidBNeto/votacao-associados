package com.davidbneto.votacao.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PautaService pautaService;

    @Test
    @DisplayName("Deve criar uma pauta")
    void criarPauta() throws Exception {
        Mockito.when(pautaService.criarPauta(Mockito.any())).thenReturn(new PautaCreationResponse(1L));
        mockMvc.perform(post("/v1/pauta")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"teste\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1}"));
    }

    @ParameterizedTest
    @DisplayName("Deve iniciar uma votação de uma pauta")
    @ValueSource(strings = {"{\"minutos\":10, \"id\":1}", "{\"id\":1}"})
    void iniciarVotacao(String body) throws Exception {
        Mockito.when(pautaService.criarPauta(Mockito.any())).thenReturn(new PautaCreationResponse(1L));
        mockMvc.perform(post("/v1/pauta/iniciar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }
}
