package com.br.edu.iff.rachaconta.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de entrada para cadastro ou atualização de um morador.")
public record MoradorDTO(
    @Schema(description = "Nome completo do morador.", example = "Ana Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    String nome,

    @Schema(description = "E-mail único do morador.", example = "ana.souza@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    String email,

    @Schema(description = "Indica se o morador está ativo.", example = "true")
    boolean ativo,

    @Schema(description = "Identificador da casa à qual o morador será vinculado.", example = "1", nullable = true)
    @Positive(message = "O identificador da casa deve ser positivo.")
    Long casaId
) {
}
