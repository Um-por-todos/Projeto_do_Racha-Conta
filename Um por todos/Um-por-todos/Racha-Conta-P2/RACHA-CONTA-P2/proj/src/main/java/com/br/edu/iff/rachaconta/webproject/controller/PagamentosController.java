package com.br.edu.iff.rachaconta.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.dto.PagamentoDTO;
import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.PagamentoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pagamentos")
public class PagamentosController {

    private final PagamentoService service;
    private final DividaService dividaService;

    public PagamentosController(PagamentoService service, DividaService dividaService) {
        this.service = service;
        this.dividaService = dividaService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("pagamentos", service.listar());
        return "pagamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pagamentoDTO", new PagamentoDTO(null, null, null, false));
        carregarDividas(model);
        return "pagamentos/form";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("pagamentoDTO") PagamentoDTO dto,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            carregarDividas(model);
            return "pagamentos/form";
        }

        service.salvar(dto);
        return "redirect:/pagamentos";
    }

    @PostMapping("/{id}/confirmar")
    public String confirmar(@PathVariable Long id) {
        service.confirmar(id);
        return "redirect:/pagamentos";
    }

    private void carregarDividas(Model model) {
        model.addAttribute(
            "dividas",
            dividaService.listar().stream().filter(divida -> !divida.isQuitada()).toList()
        );
    }
}
