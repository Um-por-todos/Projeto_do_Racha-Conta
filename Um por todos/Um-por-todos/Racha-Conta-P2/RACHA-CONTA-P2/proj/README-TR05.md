# TR05 - Persistência JPA

Esta versão substitui os repositórios em memória por Spring Data JPA com banco H2 em memória.

## Principais alterações

- Dependências `spring-boot-starter-data-jpa` e `h2` adicionadas ao Maven.
- H2 configurado em `application.properties`, com console e exibição de SQL habilitados.
- Entidades de domínio mapeadas com JPA (`@Entity`, `@Id`, `@GeneratedValue` e restrições de coluna).
- `Usuario` definido como `@MappedSuperclass` para reutilizar os campos comuns de `Morador` e `AdministradorCasa`.
- Repositórios antigos removidos e substituídos por interfaces que estendem `JpaRepository`.
- Services reorganizados para usar os métodos do Spring Data e transações nos fluxos com múltiplas gravações.
- Testes de repositório criados com `@DataJpaTest`, cobrindo gravação, busca por ID e violações de integridade.

## Executar a aplicação

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

A aplicação utiliza a porta `8080`.

## Console H2

Acesse:

```text
http://localhost:8080/h2-console
```

Configuração:

- JDBC URL: `jdbc:h2:mem:rachaconta`
- User Name: `sa`
- Password: deixar em branco

## Executar os testes

No Windows:

```bash
mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

## Entrega Git

- Branch: `TR05-persistencia`
- Pull Request: `TR05-PersistenciaJPA`
