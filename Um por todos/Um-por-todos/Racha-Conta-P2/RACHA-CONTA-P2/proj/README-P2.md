# P2 - Entrega Final

## Projeto

**Racha-Conta** - sistema de gerenciamento e divisão de despesas entre moradores de repúblicas, apartamentos e casas compartilhadas.

Esta versão consolida as entregas TR05, TR06 e TR07 e está preparada para a branch obrigatória:

```text
P2-entrega-final
```

A versão do projeto foi consolidada como **2.0.0**.

## Requisitos atendidos

### Persistência JPA + H2

- Entidades persistentes mapeadas com `@Entity`.
- Chaves com `@Id` e `@GeneratedValue`.
- Restrições com `@Column(nullable = false, unique = true, ...)` conforme o domínio.
- Repositórios baseados em `JpaRepository`.
- Repositório em memória removido.
- H2 e Hibernate configurados em `application.properties`.
- Console H2 disponível em `http://localhost:8080/h2-console`.

### Testes automatizados

A camada de repositório possui testes com `@DataJpaTest` para:

- `MoradorRepository`
- `AdministradorCasaRepository`
- `CasaRepository`
- `DespesaRepository`
- `DividaRepository`
- `PagamentoRepository`

Os testes verificam persistência, busca e restrições de integridade relevantes.

### Validações e erros MVC

- DTOs com Bean Validation.
- Controllers MVC com `@Valid` e `BindingResult`.
- Exceções personalizadas de domínio.
- Tratamento global MVC com `@ControllerAdvice`.
- Templates amigáveis em `templates/error/400.html`, `404.html` e `500.html`.

### API REST + Swagger

- API independente do MVC sob `/api/v1`.
- Controllers com `@RestController`.
- Operações REST usando GET, POST, PUT e DELETE.
- Retornos semânticos com `ResponseEntity`.
- Springdoc OpenAPI configurado.
- Swagger UI em `http://localhost:8080/swagger-ui.html`.
- OpenAPI JSON em `http://localhost:8080/v3/api-docs`.
- Documentação com `@Tag`, `@Operation`, `@ApiResponses` e `@Schema`.

### RFC 9457 / Problem Details

- `spring.mvc.problemdetails.enabled=true`.
- `@RestControllerAdvice` separado do Advice MVC.
- Advice REST estende `ResponseEntityExceptionHandler`.
- Erros de validação retornam `400 Bad Request` com `invalid_params`.
- Recurso inexistente retorna `404 Not Found` padronizado.
- Conflitos de duplicidade retornam `409 Conflict`.

## Validação local antes da entrega

Na pasta que contém o `pom.xml`:

```powershell
.\mvnw.cmd clean test
```

O esperado é `BUILD SUCCESS` e todos os testes verdes.

Depois:

```powershell
.\mvnw.cmd spring-boot:run
```

Verifique:

1. Fluxo MVC pelas telas.
2. H2 Console.
3. Swagger UI e o botão **Try it out**.
4. Payload inválido retornando 400 com `invalid_params`.
5. ID inexistente retornando 404 em Problem Details.

## Branch final

A partir da `main` atualizada:

```bash
git checkout main
git pull origin main
git checkout -b P2-entrega-final
```

Adicione e confirme a versão final:

```bash
git add .
git commit -m "Entrega final P2 - persistencia validacoes e API REST"
git push -u origin P2-entrega-final
```

## Tag da entrega

Depois de garantir que não existam alterações pendentes:

```bash
git status
git tag -a v2.0.0 -m "Entrega P2 - Persistencia, Validacoes, Tratamento de Erros e API REST"
git push origin v2.0.0
```

> A tag deve ser criada no commit final da branch `P2-entrega-final`.
