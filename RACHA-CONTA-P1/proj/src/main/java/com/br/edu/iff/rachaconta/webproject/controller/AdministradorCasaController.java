package com.br.edu.iff.rachaconta.webproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.edu.iff.rachaconta.webproject.dto.AdministradorCasaDTO;
import com.br.edu.iff.rachaconta.webproject.service.AdministradorCasaService;
@Controller @RequestMapping("/administradores") public class AdministradorCasaController{private final AdministradorCasaService s;public AdministradorCasaController(AdministradorCasaService s){this.s=s;}@GetMapping public String lista(Model m){m.addAttribute("administradores",s.listar());return "administradores/lista";}@GetMapping("/novo")public String novo(){return "administradores/form";}@PostMapping public String criar(@RequestParam String nome,@RequestParam String email,@RequestParam String nivelAcesso){s.salvar(new AdministradorCasaDTO(nome,email,nivelAcesso));return "redirect:/administradores";}@GetMapping("/{id}/editar")public String editar(@PathVariable Long id,Model m){m.addAttribute("admin",s.buscar(id));return "administradores/editar";}@PostMapping("/{id}")public String atualizar(@PathVariable Long id,@RequestParam String nome,@RequestParam String email,@RequestParam String nivelAcesso){s.atualizar(id,new AdministradorCasaDTO(nome,email,nivelAcesso));return "redirect:/administradores";}@PostMapping("/{id}/excluir")public String excluir(@PathVariable Long id){s.excluir(id);return "redirect:/administradores";}}
