package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.service.ResultadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.davidbneto.votacao.util.RandomHelper.gerarLongAleatorio;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResultadoController.class)
public class ResultadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResultadoService resultadoService;

    @Test
    @DisplayName("Deve receber o resultado da votação de uma pauta")
    void receberResultadoRequest() throws Exception {
       mockMvc.perform(post("/v1/resultado/" + gerarLongAleatorio())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
