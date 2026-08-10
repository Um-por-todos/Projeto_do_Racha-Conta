package com.br.edu.iff.rachaconta.webproject.apirest;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.edu.iff.rachaconta.webproject.service.DespesaService;
import com.br.edu.iff.rachaconta.webproject.service.DividaService;
import com.br.edu.iff.rachaconta.webproject.service.MoradorService;
import com.br.edu.iff.rachaconta.webproject.service.PagamentoService;
@RestController @RequestMapping("/api/v1") public class ApiRestController{private final MoradorService moradores;private final DespesaService despesas;private final DividaService dividas;private final PagamentoService pagamentos;public ApiRestController(MoradorService m,DespesaService d,DividaService v,PagamentoService p){moradores=m;despesas=d;dividas=v;pagamentos=p;}@GetMapping public Map<String,Object> info(){return Map.of("nome","Racha-Conta API","versao","1.0.0","status","OK","recursos",List.of("/api/v1/moradores","/api/v1/despesas","/api/v1/dividas","/api/v1/pagamentos"));}@GetMapping("/status")public Map<String,String> status(){return Map.of("status","OK","versao","1.0.0");}@GetMapping("/moradores")public Object moradores(){return moradores.listar();}@GetMapping("/despesas")public Object despesas(){return despesas.listar();}@GetMapping("/dividas")public Object dividas(){return dividas.listar();}@GetMapping("/pagamentos")public Object pagamentos(){return pagamentos.listar();}}
