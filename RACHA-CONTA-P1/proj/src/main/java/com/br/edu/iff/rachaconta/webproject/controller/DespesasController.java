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

import com.br.edu.iff.rachaconta.webproject.dto.DespesaDTO;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;
import com.br.edu.iff.rachaconta.webproject.service.DespesaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;
@Controller @RequestMapping("/despesas") public class DespesasController{private final DespesaService s;private final CasaService casas;private final MoradorService moradores;public DespesasController(DespesaService s,CasaService c,MoradorService m){this.s=s;casas=c;moradores=m;}@GetMapping public String lista(Model m){m.addAttribute("despesas",s.listar());m.addAttribute("moradores",moradores.listar());return "despesas/lista";}@GetMapping("/novo")public String novo(Model m){m.addAttribute("casas",casas.listar());m.addAttribute("moradores",moradores.listar());return "despesas/form";}@PostMapping public String criar(@RequestParam BigDecimal valorTotal,@RequestParam String descricao,@RequestParam String tipo,@RequestParam Long pagadorId,@RequestParam Long casaId,@RequestParam(required=false) LocalDate data){s.salvar(new DespesaDTO(valorTotal,descricao,tipo,pagadorId,casaId,data));return "redirect:/despesas";}@GetMapping("/{id}")public String detalhe(@PathVariable Long id,Model m){m.addAttribute("despesa",s.buscar(id));return "despesas/detalhe";}@GetMapping("/{id}/editar")public String editar(@PathVariable Long id,Model m){m.addAttribute("despesa",s.buscar(id));m.addAttribute("casas",casas.listar());m.addAttribute("moradores",moradores.listar());return "despesas/editar";}@PostMapping("/{id}")public String atualizar(@PathVariable Long id,@RequestParam BigDecimal valorTotal,@RequestParam String descricao,@RequestParam String tipo,@RequestParam Long pagadorId,@RequestParam Long casaId,@RequestParam(required=false) LocalDate data){s.atualizar(id,new DespesaDTO(valorTotal,descricao,tipo,pagadorId,casaId,data));return "redirect:/despesas";}@PostMapping("/{id}/excluir")public String excluir(@PathVariable Long id){s.excluir(id);return "redirect:/despesas";}}
