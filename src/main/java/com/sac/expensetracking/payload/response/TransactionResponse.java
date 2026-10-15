package com.sac.expensetracking.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
public class TransactionResponse {

    private UUID id;
    private BigDecimal amount;
    private String categoryName;
    private String currency;
    private String description;
    private OffsetDateTime transactionDate;

    public TransactionResponse(OffsetDateTime transactionDate, String description, String currency, String categoryName, BigDecimal amount, UUID id) {
        this.transactionDate = transactionDate;
        this.description = description;
        this.currency = currency;
        this.categoryName = categoryName;
        this.amount = amount;
        this.id = id;
    }
}
