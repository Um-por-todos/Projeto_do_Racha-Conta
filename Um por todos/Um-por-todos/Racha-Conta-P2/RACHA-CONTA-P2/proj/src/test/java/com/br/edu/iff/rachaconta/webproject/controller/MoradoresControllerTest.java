package com.br.edu.iff.rachaconta.webproject.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.br.edu.iff.rachaconta.webproject.dto.MoradorDTO;
import com.br.edu.iff.rachaconta.webproject.exception.GlobalExceptionHandler;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;

@WebMvcTest(MoradoresController.class)
@Import(GlobalExceptionHandler.class)
class MoradoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoradorService moradorService;

    @MockBean
    private CasaService casaService;

    @Test
    void deveRecarregarFormularioQuandoDadosForemInvalidos() throws Exception {
        when(casaService.listar()).thenReturn(List.of());

        mockMvc.perform(post("/moradores")
                .param("nome", "")
                .param("email", "email-invalido")
                .param("ativo", "true"))
            .andExpect(status().isOk())
            .andExpect(view().name("moradores/form"))
            .andExpect(model().attributeHasFieldErrors("moradorDTO", "nome", "email"));

        verify(moradorService, never()).salvar(any(MoradorDTO.class));
    }

    @Test
    void deveRenderizarPagina404QuandoMoradorNaoExistir() throws Exception {
        when(moradorService.buscar(999L))
            .thenThrow(new RecursoNaoEncontradoException("Morador não encontrado."));

        mockMvc.perform(get("/moradores/999"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("error/404"))
            .andExpect(model().attribute("mensagem", "Morador não encontrado."))
            .andExpect(model().attribute("codigo", 404));
    }
}
