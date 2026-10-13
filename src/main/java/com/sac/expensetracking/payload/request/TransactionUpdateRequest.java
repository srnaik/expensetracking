package com.sac.expensetracking.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionUpdateRequest {

    private UUID categoryId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private OffsetDateTime transactionDate;
}
