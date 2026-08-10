package com.br.edu.iff.rachaconta.webproject.dto;
import java.math.BigDecimal;
 import java.time.LocalDate;
public record PagamentoDTO(BigDecimal valorPago,LocalDate dataPagamento,Long dividaId,boolean confirmado) {}
