# TR06 - Validações, Exceções Personalizadas e Tratamento Global MVC

## Padrão de entrega

- Branch adotada: `feature/TR06-validacoes`
- Pull Request: `TR06-ValidacoesExcecoes`
- Destino do PR: `main`

> A descrição da atividade também menciona `TR06-ValidacoesExcecoes` como branch no passo a passo. Este projeto segue o padrão de branch informado no cabeçalho da Milestone: `feature/TR06-validacoes`.

## O que foi implementado

### Bean Validation

Foi adicionada a dependência `spring-boot-starter-validation` e os DTOs receberam validações como:

- `@NotBlank`
- `@NotNull`
- `@Size`
- `@Email`
- `@DecimalMin`

Os Controllers de Morador, Administrador, Casa, Despesa e Pagamento usam `@Valid` e `BindingResult`. Quando os dados são inválidos, o mesmo formulário é renderizado novamente com mensagens específicas ao lado dos campos.

### Exceções personalizadas

Foram criadas:

- `RecursoNaoEncontradoException`
- `RegraDeNegocioException`
- `EntidadeDuplicadaException`

Os Services lançam essas exceções em buscas inexistentes, regras de negócio inválidas e tentativas de cadastrar entidades duplicadas.

### Tratamento global MVC

A classe `GlobalExceptionHandler`, anotada com `@ControllerAdvice`, utiliza `@ExceptionHandler` para renderizar templates HTML amigáveis:

- `error/400.html`
- `error/404.html`
- `error/500.html`

Não são retornados payloads JSON para esses fluxos MVC.

### Testes

Além dos testes da TR05, foi incluído `MoradoresControllerTest`, que verifica:

1. formulário inválido permanece na própria tela e apresenta erros do `BindingResult`;
2. busca por morador inexistente é interceptada pelo `@ControllerAdvice` e renderiza `error/404`.

## Como testar

No Windows:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Depois abra:

```text
http://localhost:8080/home
```

### Testes manuais recomendados

1. Acesse **Moradores > Novo morador** e envie nome vazio ou e-mail inválido. A página deve permanecer no formulário mostrando as mensagens.
2. Cadastre dois moradores com o mesmo e-mail. A segunda tentativa deve abrir a página amigável de erro 400.
3. Acesse diretamente `/moradores/999999`. A página amigável 404 deve ser exibida.
4. Tente registrar uma despesa com pagador que não pertença à casa. A regra de negócio deve ser tratada pela página 400.

## Observação

A validação de formulário é responsabilidade dos DTOs e Controllers. Regras que dependem do estado do sistema permanecem nos Services, evitando concentrar toda a regra de negócio na camada web.
