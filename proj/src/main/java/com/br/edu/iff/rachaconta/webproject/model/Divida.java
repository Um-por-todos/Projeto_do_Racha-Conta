package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;

public class Divida {
    private Long id;
    private BigDecimal valor;
    private boolean quitada;
    private Long despesaId;
    private Long devedorId;
    private Long credorId;
    public Divida() {}
    public Divida(Long id,BigDecimal valor,boolean quitada,Long despesaId,Long devedorId,Long credorId){this.id=id;this.valor=valor;this.quitada=quitada;this.despesaId=despesaId;this.devedorId=devedorId;this.credorId=credorId;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public BigDecimal getValor(){return valor;} public void setValor(BigDecimal v){valor=v;}
    public boolean isQuitada(){return quitada;} public void setQuitada(boolean v){quitada=v;}
    public Long getDespesaId(){return despesaId;} public void setDespesaId(Long v){despesaId=v;}
    public Long getDevedorId(){return devedorId;} public void setDevedorId(Long v){devedorId=v;}
    public Long getCredorId(){return credorId;} public void setCredorId(Long v){credorId=v;}
    public void marcarComoPaga(){quitada=true;}
    public String verificarStatus(){return quitada?"Paga":"Pendente";}
}
