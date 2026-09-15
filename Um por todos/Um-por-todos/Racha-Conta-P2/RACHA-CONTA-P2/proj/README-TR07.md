# TR07 - API REST, Swagger/OpenAPI e Problem Details (RFC 9457)

## Entrega

- Branch: `TR07-api-rest`
- Pull Request: `TR07-ApiRestSwagger`
- Base da API: `/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## O que foi implementado

1. Controllers REST separados dos controllers MVC.
2. Endpoints REST para moradores, casas, administradores, despesas, dívidas e pagamentos.
3. Uso de `ResponseEntity` com respostas semânticas:
   - `200 OK` em consultas e atualizações;
   - `201 Created` em cadastros;
   - `204 No Content` em exclusões.
4. Bean Validation em DTOs com `@Valid @RequestBody`.
5. DTOs documentados com `@Schema`, descrições e exemplos.
6. Controllers documentados com `@Tag`, `@Operation` e `@ApiResponse/@ApiResponses`.
7. Springdoc OpenAPI compatível com Spring Boot 3.5.x.
8. `spring.mvc.problemdetails.enabled=true`.
9. `ApiExceptionHandler` usando `@RestControllerAdvice` e `ResponseEntityExceptionHandler`.
10. Respostas de erro utilizando `ProblemDetail` no padrão RFC 9457.
11. Erros de Bean Validation retornam a propriedade `invalid_params`.
12. Testes MockMvc da API REST para status 200, 201, 204, 400 e 404.

## Exemplo de payload

### POST /api/v1/moradores

```json
{
  "nome": "Ana Souza",
  "email": "ana.souza@email.com",
  "ativo": true,
  "casaId": 1
}
```

## Exemplo de erro de validação

```json
{
  "type": "https://rachaconta.local/problemas/dados-invalidos",
  "title": "Dados de entrada inválidos",
  "status": 400,
  "detail": "Um ou mais campos enviados não passaram pela validação.",
  "instance": "/api/v1/moradores",
  "invalid_params": [
    {
      "campo": "email",
      "mensagem": "Informe um e-mail válido."
    }
  ]
}
```

## Como testar

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Depois acesse `http://localhost:8080/swagger-ui.html` e utilize **Try it out**.
