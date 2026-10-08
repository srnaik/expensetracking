package com.sac.expensetracking.payload.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransactionResponse {

    private BigDecimal amount;
    private String categoryName;
    private String currency;
    private String description;
    private OffsetDateTime transactionDate;

    public TransactionResponse(BigDecimal amount, String categoryName, String currency, String description, OffsetDateTime transactionDate) {
        this.amount = amount;
        this.categoryName = categoryName;
        this.currency = currency;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
