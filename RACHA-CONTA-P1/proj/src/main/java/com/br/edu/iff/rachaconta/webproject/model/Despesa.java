package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Despesa {
    private Long id;
    private BigDecimal valorTotal;
    private String descricao;
    private String tipo;
    private Long pagadorId;
    private Long casaId;
    private LocalDate data;
    public Despesa() {}
    public Despesa(Long id,BigDecimal valorTotal,String descricao,String tipo,Long pagadorId,Long casaId,LocalDate data){this.id=id;this.valorTotal=valorTotal;this.descricao=descricao;this.tipo=tipo;this.pagadorId=pagadorId;this.casaId=casaId;this.data=data;}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public BigDecimal getValorTotal(){return valorTotal;} public void setValorTotal(BigDecimal v){this.valorTotal=v;}
    public String getDescricao(){return descricao;} public void setDescricao(String v){this.descricao=v;}
    public String getTipo(){return tipo;} public void setTipo(String v){this.tipo=v;}
    public Long getPagadorId(){return pagadorId;} public void setPagadorId(Long v){this.pagadorId=v;}
    public Long getCasaId(){return casaId;} public void setCasaId(Long v){this.casaId=v;}
    public LocalDate getData(){return data;} public void setData(LocalDate v){this.data=v;}
    public BigDecimal calcularValorIndividual(int quantidade){if(quantidade<=0) throw new IllegalArgumentException("A casa precisa possuir moradores ativos."); return valorTotal.divide(BigDecimal.valueOf(quantidade),2,RoundingMode.HALF_UP);}
    public BigDecimal dividirDespesa(int quantidade){return calcularValorIndividual(quantidade);}
}
