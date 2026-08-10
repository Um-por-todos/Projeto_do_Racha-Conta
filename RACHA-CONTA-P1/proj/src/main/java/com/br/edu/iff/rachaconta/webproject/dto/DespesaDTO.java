package com.br.edu.iff.rachaconta.webproject.dto;
import java.math.BigDecimal;
 import java.time.LocalDate;
public record DespesaDTO(BigDecimal valorTotal,String descricao,String tipo,Long pagadorId,Long casaId,LocalDate data) {}
