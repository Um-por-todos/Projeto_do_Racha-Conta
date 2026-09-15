package com.br.edu.iff.rachaconta.webproject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.dto.DespesaDTO;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.DespesaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/despesas")
public class DespesasController {

    private final DespesaService service;
    private final CasaService casaService;
    private final MoradorService moradorService;

    public DespesasController(
        DespesaService service,
        CasaService casaService,
        MoradorService moradorService
    ) {
        this.service = service;
        this.casaService = casaService;
        this.moradorService = moradorService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("despesas", service.listar());
        model.addAttribute("moradores", moradorService.listar());

        Map<Long, String> pagadorNomes = new HashMap<>();
        moradorService.listar().forEach(
            morador -> pagadorNomes.put(morador.getId(), morador.getNome())
        );
        model.addAttribute("pagadorNomes", pagadorNomes);
        return "despesas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("despesaDTO", new DespesaDTO(null, "", "", null, null, null));
        carregarOpcoes(model);
        return "despesas/form";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("despesaDTO") DespesaDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            carregarOpcoes(model);
            return "despesas/form";
        }

        service.salvar(dto);
        return "redirect:/despesas";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Despesa despesa = service.buscar(id);
        model.addAttribute("despesa", despesa);
        model.addAttribute(
            "pagadorNome",
            moradorService.buscar(despesa.getPagadorId()).getNome()
        );
        return "despesas/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Despesa despesa = service.buscar(id);
        model.addAttribute("despesaId", id);
        model.addAttribute("despesaDTO", new DespesaDTO(
            despesa.getValorTotal(),
            despesa.getDescricao(),
            despesa.getTipo(),
            despesa.getPagadorId(),
            despesa.getCasaId(),
            despesa.getData()
        ));
        carregarOpcoes(model);
        return "despesas/editar";
    }

    @PostMapping("/{id}")
    public String atualizar(
        @PathVariable Long id,
        @Valid @ModelAttribute("despesaDTO") DespesaDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("despesaId", id);
            carregarOpcoes(model);
            return "despesas/editar";
        }

        service.atualizar(id, dto);
        return "redirect:/despesas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/despesas";
    }

    private void carregarOpcoes(Model model) {
        model.addAttribute("casas", casaService.listar());
        model.addAttribute("moradores", moradorService.listar());
    }
}
