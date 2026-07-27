package com.nilton.gerenciamento_assinatura.repository;

import com.nilton.gerenciamento_assinatura.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findByUser_id(Long id); // vai retornar as assinaturas do user

    /*@Query(value = "SELECT a FROM Assinatura a WHERE a.user.id = :id")
    List<Assinatura> findByUserid(@Param("id") Long id);*/

    @Query(value = "SELECT a FROM assinaturas a WHERE a.user_id = :id", nativeQuery = true)
    List<Assinatura> findByUserid(@Param("id") Long id);
}
