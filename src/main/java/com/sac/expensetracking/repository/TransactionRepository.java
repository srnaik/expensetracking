package com.sac.expensetracking.repository;

import com.sac.expensetracking.models.Category;
import com.sac.expensetracking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
    SELECT t
    FROM Transaction t
    WHERE t.user.id = :userId""")
    List<Transaction> findAllTransactions(@Param("userId") UUID userId);
}
