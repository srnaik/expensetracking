package com.sac.expensetracking.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionRequest {


    private UUID categoryId;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private String description;
    @NotNull
    private OffsetDateTime transactionDate;


    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(OffsetDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
