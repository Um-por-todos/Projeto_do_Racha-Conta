package com.br.edu.iff.rachaconta.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.iff.rachaconta.webproject.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
