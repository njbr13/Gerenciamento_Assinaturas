package com.nilton.gerenciamento_assinatura.repository;

import com.nilton.gerenciamento_assinatura.model.HistoricoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoPagRepository extends JpaRepository<HistoricoPagamento, Long> {
}
