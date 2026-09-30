package com.sac.expensetracking.repository;

import com.sac.expensetracking.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> finduserById(Long userId);
}
