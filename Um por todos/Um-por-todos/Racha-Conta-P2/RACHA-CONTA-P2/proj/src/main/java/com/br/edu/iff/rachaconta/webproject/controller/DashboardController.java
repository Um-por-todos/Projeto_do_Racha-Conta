package com.br.edu.iff.rachaconta.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.DespesaService;
import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final MoradorService moradorService;
    private final CasaService casaService;
    private final DespesaService despesaService;
    private final DividaService dividaService;

    public DashboardController(
        MoradorService moradorService,
        CasaService casaService,
        DespesaService despesaService,
        DividaService dividaService
    ) {
        this.moradorService = moradorService;
        this.casaService = casaService;
        this.despesaService = despesaService;
        this.dividaService = dividaService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("moradores", moradorService.listar().size());
        model.addAttribute("casas", casaService.listar().size());
        model.addAttribute("despesas", despesaService.listar().size());
        model.addAttribute(
            "dividas",
            dividaService.listar().stream().filter(divida -> !divida.isQuitada()).count()
        );
        return "dashboard";
    }
}
