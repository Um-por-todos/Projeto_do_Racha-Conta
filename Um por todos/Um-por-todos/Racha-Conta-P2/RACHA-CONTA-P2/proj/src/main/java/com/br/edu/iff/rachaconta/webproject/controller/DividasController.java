package com.br.edu.iff.rachaconta.webproject.controller;

import java.util.function.Function;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.RelatorioFinanceiroService;

@Controller
@RequestMapping("/dividas")
public class DividasController {

    private final DividaService service;
    private final RelatorioFinanceiroService relatorioService;

    public DividasController(
        DividaService service,
        RelatorioFinanceiroService relatorioService
    ) {
        this.service = service;
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("dividas", service.listar());
        model.addAttribute("nome", (Function<Long, String>) relatorioService::nome);
        return "dividas/lista";
    }

    @PostMapping("/{id}/pagar")
    public String pagar(@PathVariable Long id) {
        service.marcarComoPaga(id);
        return "redirect:/dividas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/dividas";
    }
}
