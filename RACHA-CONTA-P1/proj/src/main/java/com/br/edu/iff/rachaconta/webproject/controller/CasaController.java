package com.br.edu.iff.rachaconta.webproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.edu.iff.rachaconta.webproject.dto.CasaDTO;
import com.br.edu.iff.rachaconta.webproject.service.CasaService;
@Controller @RequestMapping("/casa") public class CasaController{private final CasaService s;public CasaController(CasaService s){this.s=s;}@GetMapping public String lista(Model m){m.addAttribute("casas",s.listar());return "casas/lista";}@GetMapping("/novo")public String novo(){return "casas/form";}@PostMapping public String criar(@RequestParam String nome,@RequestParam String endereco){s.salvar(new CasaDTO(nome,endereco));return "redirect:/casa";}@GetMapping("/{id}")public String detalhe(@PathVariable Long id,Model m){m.addAttribute("casa",s.buscar(id));m.addAttribute("moradores",s.listarMoradores(id));return "casas/detalhe";}@GetMapping("/{id}/editar")public String editar(@PathVariable Long id,Model m){m.addAttribute("casa",s.buscar(id));return "casas/editar";}@PostMapping("/{id}")public String atualizar(@PathVariable Long id,@RequestParam String nome,@RequestParam String endereco){s.atualizar(id,new CasaDTO(nome,endereco));return "redirect:/casa";}@PostMapping("/{id}/excluir")public String excluir(@PathVariable Long id){s.excluir(id);return "redirect:/casa";}}
