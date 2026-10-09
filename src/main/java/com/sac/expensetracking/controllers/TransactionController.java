package com.sac.expensetracking.controllers;


import com.sac.expensetracking.models.Category;
import com.sac.expensetracking.models.Transaction;
import com.sac.expensetracking.models.User;
import com.sac.expensetracking.payload.request.TransactionRequest;
import com.sac.expensetracking.payload.response.CategoryResponse;
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
import java.util.List;
import java.util.UUID;

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
                savedTransaction.getTransactionDate(),savedTransaction.getDescription(),savedTransaction.getCurrency(),
                savedTransaction.getCategory().getName(),savedTransaction.getAmount(),
                savedTransaction.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping
    public ResponseEntity<?> getTransactions(@AuthenticationPrincipal UserDetailsImpl currentUser){
        List<TransactionResponse> response =
                transactionRepository
                        .findAllTransactions(currentUser.getId())
                        .stream()
                        .map(transaction -> new TransactionResponse(
                                transaction.getTransactionDate(),
                                transaction.getDescription(),
                                transaction.getCurrency(),
                                transaction.getCategory().getName(),
                                transaction.getAmount(),
                                transaction.getId()
                        ))
                        .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Transaction not found with id: " + id
                ));

        TransactionResponse response = new TransactionResponse(
                transaction.getTransactionDate(),
                transaction.getDescription(),
                transaction.getCurrency(),
                transaction.getCategory().getName(),
                transaction.getAmount(),
                transaction.getId()
        );
        return ResponseEntity.ok(response);
    }

}
