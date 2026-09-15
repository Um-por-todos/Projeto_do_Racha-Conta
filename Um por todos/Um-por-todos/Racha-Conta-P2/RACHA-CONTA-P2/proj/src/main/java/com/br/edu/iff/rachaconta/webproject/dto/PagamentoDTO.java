package com.br.edu.iff.rachaconta.webproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados de entrada para cadastro ou atualização de um pagamento.")
public record PagamentoDTO(
    @Schema(description = "Valor pago.", example = "90.25", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor pago é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor pago deve ser maior que zero.")
    BigDecimal valorPago,

    @Schema(description = "Data do pagamento. Se omitida, será utilizada a data atual.", example = "2026-09-11", nullable = true)
    LocalDate dataPagamento,

    @Schema(description = "ID da dívida quitada pelo pagamento.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione uma dívida.")
    @Positive(message = "O identificador da dívida deve ser positivo.")
    Long dividaId,

    @Schema(description = "Indica se o pagamento já foi confirmado.", example = "true")
    boolean confirmado
) {
}
