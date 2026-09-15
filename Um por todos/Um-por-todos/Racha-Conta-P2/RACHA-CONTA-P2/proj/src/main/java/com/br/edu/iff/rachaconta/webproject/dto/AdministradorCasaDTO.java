package com.br.edu.iff.rachaconta.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de entrada para cadastro ou atualização de um administrador.")
public record AdministradorCasaDTO(
    @Schema(description = "Nome completo do administrador.", example = "Carlos Lima", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    String nome,

    @Schema(description = "E-mail único do administrador.", example = "carlos.lima@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    String email,

    @Schema(description = "Nível de acesso do administrador.", example = "TOTAL", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nível de acesso é obrigatório.")
    @Size(max = 30, message = "O nível de acesso deve ter no máximo 30 caracteres.")
    String nivelAcesso
) {
}
