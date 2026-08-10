package com.br.edu.iff.rachaconta.webproject.dto;
import java.math.BigDecimal;
public record DividaDTO(BigDecimal valor,Long despesaId,Long devedorId,Long credorId) {}
