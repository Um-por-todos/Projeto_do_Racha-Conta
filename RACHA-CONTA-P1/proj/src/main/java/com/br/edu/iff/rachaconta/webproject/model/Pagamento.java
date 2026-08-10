package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {
    private Long id;
    private BigDecimal valorPago;
    private LocalDate dataPagamento;
    private Long dividaId;
    private boolean confirmado;
    public Pagamento() {}
    public Pagamento(Long id,BigDecimal valorPago,LocalDate dataPagamento,Long dividaId,boolean confirmado){this.id=id;this.valorPago=valorPago;this.dataPagamento=dataPagamento;this.dividaId=dividaId;this.confirmado=confirmado;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public BigDecimal getValorPago(){return valorPago;} public void setValorPago(BigDecimal v){valorPago=v;}
    public LocalDate getDataPagamento(){return dataPagamento;} public void setDataPagamento(LocalDate v){dataPagamento=v;}
    public Long getDividaId(){return dividaId;} public void setDividaId(Long v){dividaId=v;}
    public boolean isConfirmado(){return confirmado;} public void setConfirmado(boolean v){confirmado=v;}
    public void confirmarPagamento(){confirmado=true;}
}
