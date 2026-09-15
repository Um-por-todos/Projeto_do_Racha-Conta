package com.br.edu.iff.rachaconta.webproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de entrada para cadastro ou atualização de uma casa.")
public record CasaDTO(
    @Schema(description = "Nome de identificação da casa.", example = "República Central", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome da casa é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    String nome,

    @Schema(description = "Endereço da casa.", example = "Rua das Flores, 120", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O endereço é obrigatório.")
    @Size(min = 5, max = 200, message = "O endereço deve ter entre 5 e 200 caracteres.")
    String endereco
) {
}
