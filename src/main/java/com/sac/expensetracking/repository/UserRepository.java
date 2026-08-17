package com.sac.expensetracking.repository;

import com.sac.expensetracking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    Boolean existsByUsername(String email);
    Boolean existsByEmail(String email);
}
