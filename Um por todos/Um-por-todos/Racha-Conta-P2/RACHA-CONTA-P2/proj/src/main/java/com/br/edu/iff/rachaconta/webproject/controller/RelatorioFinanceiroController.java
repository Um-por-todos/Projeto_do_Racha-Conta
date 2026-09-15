package com.br.edu.iff.rachaconta.webproject.controller;

import java.util.function.Function;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.service.RelatorioFinanceiroService;

@Controller
@RequestMapping("/relatorio")
public class RelatorioFinanceiroController {

    private final RelatorioFinanceiroService service;

    public RelatorioFinanceiroController(RelatorioFinanceiroService service) {
        this.service = service;
    }

    @GetMapping
    public String relatorio(Model model) {
        model.addAttribute("relatorio", service.gerar());
        model.addAttribute("dividas", service.pendentes());
        model.addAttribute("nome", (Function<Long, String>) service::nome);
        return "relatorio";
    }
}
