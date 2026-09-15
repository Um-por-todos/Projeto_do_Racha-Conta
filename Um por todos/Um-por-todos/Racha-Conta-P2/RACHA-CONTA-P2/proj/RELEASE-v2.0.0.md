## 🚀 Visão Geral
Segunda versão funcional do projeto de gerenciamento e divisão de despesas entre moradores de repúblicas, apartamentos e casas compartilhadas.

Esta release representa a entrega da Avaliação P2, substituindo a infraestrutura em memória por persistência em banco de dados, implementando tratamento robusto de erros e disponibilizando uma API REST documentada. Código entregue através da branch `P2-entrega-final`.

## ⚙️ Funcionalidades Entregues
- **Persistência de Dados (TR05):** Banco H2 integrado utilizando Spring Data JPA, entidades mapeadas e camada de repositório validada via testes automatizados (JUnit).
- **Validações e Exceções MVC (TR06):** Bean Validation implementado nos formulários e tratamento centralizado (`@ControllerAdvice`) direcionando para páginas de erro amigáveis.
- **API RESTful e Swagger (TR07):**
  - Controladores independentes mapeados de forma semântica.
  - Documentação interativa disponível via Swagger UI.
  - Respostas de erro padronizadas pela RFC 9457 (Problem Details) via `@RestControllerAdvice`.

## 🛠️ Status do Sistema
- [ ] O fluxo MVC (telas) continua operando normalmente, agora conectado ao H2.
- [ ] A API REST atende aos requisitos de validação e roteamento.
- [ ] Todos os testes automatizados da camada de repositório estão com status de sucesso.

## 🔗 Endereços úteis durante a execução
- Aplicação MVC: `http://localhost:8080/`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`
