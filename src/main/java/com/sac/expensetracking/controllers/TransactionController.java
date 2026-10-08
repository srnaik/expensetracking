package com.sac.expensetracking.controllers;


import com.sac.expensetracking.models.Category;
import com.sac.expensetracking.models.Transaction;
import com.sac.expensetracking.models.User;
import com.sac.expensetracking.payload.request.TransactionRequest;
import com.sac.expensetracking.payload.response.TransactionResponse;
import com.sac.expensetracking.repository.CategoryRepository;
import com.sac.expensetracking.repository.TransactionRepository;
import com.sac.expensetracking.repository.UserRepository;
import com.sac.expensetracking.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest, @AuthenticationPrincipal UserDetailsImpl currentUser){

        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new RuntimeException("User Not Found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setTransactionDate(transactionRequest.getTransactionDate());
        transaction.setCreatedAt(OffsetDateTime.now());
        transaction.setUser(user);

        Category category = categoryRepository.findById(transactionRequest.getCategoryId()).orElseThrow(
                () -> new RuntimeException("Category Not Found"));

        transaction.setCategory(category);

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionResponse transactionResponse = new TransactionResponse(
                savedTransaction.getAmount(), savedTransaction.getCategory().getName(), savedTransaction.getCurrency(),
                savedTransaction.getDescription(), savedTransaction.getTransactionDate()
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

}
