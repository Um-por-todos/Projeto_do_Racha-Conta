package com.br.edu.iff.rachaconta.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.dto.MoradorDTO;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/moradores")
public class MoradoresController {

    private final MoradorService service;
    private final CasaService casaService;

    public MoradoresController(MoradorService service, CasaService casaService) {
        this.service = service;
        this.casaService = casaService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("moradores", service.listar());
        return "moradores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("moradorDTO", new MoradorDTO("", "", true, null));
        carregarCasas(model);
        return "moradores/form";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("moradorDTO") MoradorDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            carregarCasas(model);
            return "moradores/form";
        }

        service.salvar(dto);
        return "redirect:/moradores";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("morador", service.buscar(id));
        return "moradores/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Morador morador = service.buscar(id);
        model.addAttribute("moradorId", id);
        model.addAttribute("moradorDTO", new MoradorDTO(
            morador.getNome(),
            morador.getEmail(),
            morador.isAtivo(),
            encontrarCasaDoMorador(id)
        ));
        carregarCasas(model);
        return "moradores/editar";
    }

    @PostMapping("/{id}")
    public String atualizar(
        @PathVariable Long id,
        @Valid @ModelAttribute("moradorDTO") MoradorDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("moradorId", id);
            carregarCasas(model);
            return "moradores/editar";
        }

        service.atualizar(id, dto);
        return "redirect:/moradores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/moradores";
    }

    private void carregarCasas(Model model) {
        model.addAttribute("casas", casaService.listar());
    }

    private Long encontrarCasaDoMorador(Long moradorId) {
        return casaService.listar().stream()
            .filter(casa -> casa.getMoradoresIds().contains(moradorId))
            .map(casa -> casa.getId())
            .findFirst()
            .orElse(null);
    }
}
