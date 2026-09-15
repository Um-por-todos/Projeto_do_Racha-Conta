package com.br.edu.iff.rachaconta.webproject.apirest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.edu.iff.rachaconta.webproject.dto.DespesaDTO;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.service.DespesaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/despesas")
@Tag(name = "Despesas", description = "Operações REST relacionadas a despesas.")
public class DespesaRestController {

    private final DespesaService service;

    public DespesaRestController(DespesaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista despesas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Despesa>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca despesa por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public ResponseEntity<Despesa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra despesa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada"),
        @ApiResponse(responseCode = "409", description = "Entidade duplicada")
    })
    public ResponseEntity<Despesa> criar(@Valid @RequestBody DespesaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza despesa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "409", description = "Entidade duplicada")
    })
    public ResponseEntity<Despesa> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody DespesaDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui despesa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recurso excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
