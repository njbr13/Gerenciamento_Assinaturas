package com.nilton.gerenciamento_assinatura.repository;

import com.nilton.gerenciamento_assinatura.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{


    Optional<User> findByEmail(String email);

    Optional<User> findByNome(String nome);

    Optional<User> findByResetToken(String token);


}
