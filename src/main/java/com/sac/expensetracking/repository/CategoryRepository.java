package com.sac.expensetracking.repository;

import com.sac.expensetracking.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("""
    SELECT c
    FROM Category c
    WHERE c.user.id = :userId
       OR c.user IS NULL
""")
    List<Category> findAvailableCategories(@Param("userId") UUID userId);
}
