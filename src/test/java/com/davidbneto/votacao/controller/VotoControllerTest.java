package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.service.VotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.davidbneto.votacao.util.RandomHelper.gerarObjetoAleatorioComoString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VotoService votoService;

    @Test
    @DisplayName("Deve votar com sucesso")
    void votar() throws Exception {
        mockMvc.perform(post("/v1/voto")
                        .contentType(APPLICATION_JSON)
                        .content(gerarObjetoAleatorioComoString(VotoRequest.class)))
                .andExpect(status().isOk());
    }

}
