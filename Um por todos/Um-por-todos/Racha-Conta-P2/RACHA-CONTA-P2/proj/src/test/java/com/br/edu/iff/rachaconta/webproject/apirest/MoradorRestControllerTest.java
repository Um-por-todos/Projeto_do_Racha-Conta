package com.br.edu.iff.rachaconta.webproject.apirest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.br.edu.iff.rachaconta.webproject.apirest.error.ApiExceptionHandler;
import com.br.edu.iff.rachaconta.webproject.dto.MoradorDTO;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;

@WebMvcTest(MoradorRestController.class)
@Import(ApiExceptionHandler.class)
class MoradorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoradorService service;

    @Test
    void deveListarMoradoresComStatus200() throws Exception {
        when(service.listar()).thenReturn(List.of(
            new Morador(1L, "Ana Souza", "ana@email.com", true)
        ));

        mockMvc.perform(get("/api/v1/moradores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nome").value("Ana Souza"));
    }

    @Test
    void deveCriarMoradorComStatus201() throws Exception {
        when(service.salvar(any(MoradorDTO.class)))
            .thenReturn(new Morador(1L, "Ana Souza", "ana@email.com", true));

        mockMvc.perform(post("/api/v1/moradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nome": "Ana Souza",
                      "email": "ana@email.com",
                      "ativo": true,
                      "casaId": null
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornarProblemDetailComInvalidParamsQuandoPayloadForInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/moradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nome": "",
                      "email": "email-invalido",
                      "ativo": true,
                      "casaId": -1
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Dados de entrada inválidos"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.invalid_params").isArray());
    }

    @Test
    void deveRetornar404EmProblemDetailQuandoMoradorNaoExistir() throws Exception {
        when(service.buscar(999L))
            .thenThrow(new RecursoNaoEncontradoException("Morador não encontrado."));

        mockMvc.perform(get("/api/v1/moradores/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
            .andExpect(jsonPath("$.detail").value("Morador não encontrado."))
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void deveExcluirMoradorComStatus204() throws Exception {
        doNothing().when(service).excluir(1L);

        mockMvc.perform(delete("/api/v1/moradores/1"))
            .andExpect(status().isNoContent());

        verify(service).excluir(1L);
    }
}
