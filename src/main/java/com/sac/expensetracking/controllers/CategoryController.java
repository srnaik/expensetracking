package com.sac.expensetracking.controllers;

import com.sac.expensetracking.models.Category;
import com.sac.expensetracking.models.User;
import com.sac.expensetracking.payload.request.CategoryRequest;
import com.sac.expensetracking.payload.response.CategoryResponse;
import com.sac.expensetracking.repository.CategoryRepository;
import com.sac.expensetracking.repository.UserRepository;
import com.sac.expensetracking.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request, @AuthenticationPrincipal UserDetailsImpl currentUser){


        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new RuntimeException("User Not Found"));

        Category category = new Category();

        category.setName(request.getCategoryName());
        category.setType(request.getType());
        category.setIcon(request.getIcon());
        category.setColor(request.getColor());
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);


        CategoryResponse response = new CategoryResponse(
                savedCategory.getName(),
                savedCategory.getType(),
                savedCategory.getIcon(),
                savedCategory.getColor()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
