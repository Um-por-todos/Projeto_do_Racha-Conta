package com.br.edu.iff.rachaconta.webproject.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados de entrada para cadastro ou atualização de uma dívida.")
public record DividaDTO(
    @Schema(description = "Valor da dívida.", example = "90.25", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor da dívida é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor da dívida deve ser maior que zero.")
    BigDecimal valor,

    @Schema(description = "ID da despesa que originou a dívida.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A despesa é obrigatória.")
    @Positive(message = "O identificador da despesa deve ser positivo.")
    Long despesaId,

    @Schema(description = "ID do morador devedor.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O devedor é obrigatório.")
    @Positive(message = "O identificador do devedor deve ser positivo.")
    Long devedorId,

    @Schema(description = "ID do morador credor.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O credor é obrigatório.")
    @Positive(message = "O identificador do credor deve ser positivo.")
    Long credorId
) {
}
