package com.br.edu.iff.rachaconta.webproject.controller;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.edu.iff.rachaconta.webproject.dto.PagamentoDTO;
import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.PagamentoService;
@Controller @RequestMapping("/pagamentos") public class PagamentosController{private final PagamentoService s;private final DividaService dividas;public PagamentosController(PagamentoService s,DividaService d){this.s=s;dividas=d;}@GetMapping public String lista(Model m){m.addAttribute("pagamentos",s.listar());return "pagamentos/lista";}@GetMapping("/novo")public String novo(Model m){m.addAttribute("dividas",dividas.listar().stream().filter(d->!d.isQuitada()).toList());return "pagamentos/form";}@PostMapping public String criar(@RequestParam BigDecimal valorPago,@RequestParam Long dividaId,@RequestParam(required=false) LocalDate dataPagamento,@RequestParam(defaultValue="false") boolean confirmado){s.salvar(new PagamentoDTO(valorPago,dataPagamento,dividaId,confirmado));return "redirect:/pagamentos";}@PostMapping("/{id}/confirmar")public String confirmar(@PathVariable Long id){s.confirmar(id);return "redirect:/pagamentos";}}
