package com.br.edu.iff.rachaconta.webproject.apirest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "API", description = "Informações gerais da API Racha-Conta")
public class ApiInfoRestController {

    @GetMapping
    @Operation(summary = "Exibe informações e recursos disponíveis na API")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
            "nome", "Racha-Conta API",
            "versao", "v1",
            "status", "OK",
            "recursos", List.of(
                "/api/v1/moradores",
                "/api/v1/casas",
                "/api/v1/administradores",
                "/api/v1/despesas",
                "/api/v1/dividas",
                "/api/v1/pagamentos"
            )
        ));
    }

    @GetMapping("/status")
    @Operation(summary = "Verifica se a API está disponível")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of("status", "OK", "versao", "v1"));
    }
}
