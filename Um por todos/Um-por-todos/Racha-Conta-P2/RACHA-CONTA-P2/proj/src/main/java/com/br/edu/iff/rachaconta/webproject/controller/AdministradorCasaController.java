package com.br.edu.iff.rachaconta.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.dto.AdministradorCasaDTO;
import com.br.edu.iff.rachaconta.webproject.model.AdministradorCasa;
import com.br.edu.iff.rachaconta.webproject.service.AdministradorCasaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/administradores")
public class AdministradorCasaController {

    private final AdministradorCasaService service;

    public AdministradorCasaController(AdministradorCasaService service) {
        this.service = service;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("administradores", service.listar());
        return "administradores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("administradorDTO", new AdministradorCasaDTO("", "", "ADMIN"));
        return "administradores/form";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("administradorDTO") AdministradorCasaDTO dto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "administradores/form";
        }

        service.salvar(dto);
        return "redirect:/administradores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        AdministradorCasa administrador = service.buscar(id);
        model.addAttribute("administradorId", id);
        model.addAttribute("administradorDTO", new AdministradorCasaDTO(
            administrador.getNome(),
            administrador.getEmail(),
            administrador.getNivelAcesso()
        ));
        return "administradores/editar";
    }

    @PostMapping("/{id}")
    public String atualizar(
        @PathVariable Long id,
        @Valid @ModelAttribute("administradorDTO") AdministradorCasaDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("administradorId", id);
            return "administradores/editar";
        }

        service.atualizar(id, dto);
        return "redirect:/administradores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/administradores";
    }
}
