package com.br.edu.iff.rachaconta.webproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de entrada para cadastro ou atualização de uma despesa.")
public record DespesaDTO(
    @Schema(description = "Valor total da despesa.", example = "180.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor total é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor total deve ser maior que zero.")
    BigDecimal valorTotal,

    @Schema(description = "Descrição da despesa.", example = "Compra do supermercado", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 2, max = 150, message = "A descrição deve ter entre 2 e 150 caracteres.")
    String descricao,

    @Schema(description = "Categoria da despesa.", example = "ALIMENTACAO", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O tipo é obrigatório.")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres.")
    String tipo,

    @Schema(description = "ID do morador que pagou a despesa.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione quem pagou a despesa.")
    @Positive(message = "O identificador do pagador deve ser positivo.")
    Long pagadorId,

    @Schema(description = "ID da casa à qual a despesa pertence.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione a casa da despesa.")
    @Positive(message = "O identificador da casa deve ser positivo.")
    Long casaId,

    @Schema(description = "Data da despesa. Se omitida, a regra de negócio define a data aplicável.", example = "2026-09-11", nullable = true)
    LocalDate data
) {
}
