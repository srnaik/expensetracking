package com.sac.expensetracking.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {

    private UUID categoryId;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private String description;
    @NotNull
    private OffsetDateTime transactionDate;


}
