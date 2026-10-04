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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest categoryRequest,
                                            @AuthenticationPrincipal UserDetailsImpl currentUser) {


        Optional<Category> existingCategory =  categoryRepository.findById(id);

        if(existingCategory.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Category category = existingCategory.get();

        // Make sure this category belongs to logged-in user
        if (!category.getUser().getId()
                .equals(currentUser.getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to update this category");
        }


        category.setName(categoryRequest.getCategoryName());
        category.setColor(categoryRequest.getColor());
        category.setIcon(categoryRequest.getIcon());
        category.setType(categoryRequest.getType());

        Category updatedExpense = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                updatedExpense.getName(),
                updatedExpense.getType(),
                updatedExpense.getIcon(),
                updatedExpense.getColor()
        );

        return ResponseEntity.ok(categoryResponse);
    }


    @GetMapping
    public ResponseEntity<?> getCategories(@AuthenticationPrincipal UserDetailsImpl currentUser){

        List<CategoryResponse> response =
                categoryRepository
                        .findAvailableCategories(currentUser.getId())
                        .stream()
                        .map(category -> new CategoryResponse(
                                category.getName(),
                                category.getType(),
                                category.getIcon(),
                                category.getColor()
                        ))
                        .toList();
        return ResponseEntity.ok(response);
    }
}
