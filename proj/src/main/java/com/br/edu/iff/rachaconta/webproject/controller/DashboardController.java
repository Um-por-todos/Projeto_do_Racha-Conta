package com.br.edu.iff.rachaconta.webproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.DespesaService;
import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;
@Controller @RequestMapping("/dashboard") public class DashboardController{private final MoradorService m;private final CasaService c;private final DespesaService d;private final DividaService v;public DashboardController(MoradorService m,CasaService c,DespesaService d,DividaService v){this.m=m;this.c=c;this.d=d;this.v=v;}@GetMapping public String index(Model x){x.addAttribute("moradores",m.listar().size());x.addAttribute("casas",c.listar().size());x.addAttribute("despesas",d.listar().size());x.addAttribute("dividas",v.listar().stream().filter(q->!q.isQuitada()).count());return "dashboard";}}
