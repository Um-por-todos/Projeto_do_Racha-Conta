package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Pagamento;
@Repository public class PagamentoRepository extends InMemoryRepository<Pagamento>{public PagamentoRepository(){super(Pagamento::getId,Pagamento::setId);}}
