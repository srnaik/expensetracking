package com.sac.expensetracking.payload.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionUpdateRequest {

    private UUID categoryId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private OffsetDateTime transactionDate;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public OffsetDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(OffsetDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
