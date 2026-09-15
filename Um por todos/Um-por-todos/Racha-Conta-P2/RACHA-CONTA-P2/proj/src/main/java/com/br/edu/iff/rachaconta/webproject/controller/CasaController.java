package com.br.edu.iff.rachaconta.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.dto.CasaDTO;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/casa")
public class CasaController {

    private final CasaService service;

    public CasaController(CasaService service) {
        this.service = service;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("casas", service.listar());
        return "casas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("casaDTO", new CasaDTO("", ""));
        return "casas/form";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("casaDTO") CasaDTO dto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "casas/form";
        }

        service.salvar(dto);
        return "redirect:/casa";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("casa", service.buscar(id));
        model.addAttribute("moradores", service.listarMoradores(id));
        return "casas/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Casa casa = service.buscar(id);
        model.addAttribute("casaId", id);
        model.addAttribute("casaDTO", new CasaDTO(casa.getNome(), casa.getEndereco()));
        return "casas/editar";
    }

    @PostMapping("/{id}")
    public String atualizar(
        @PathVariable Long id,
        @Valid @ModelAttribute("casaDTO") CasaDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("casaId", id);
            return "casas/editar";
        }

        service.atualizar(id, dto);
        return "redirect:/casa";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/casa";
    }
}
