package com.sac.expensetracking.payload.request;

import com.sac.expensetracking.util.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank
    private String categoryName;

    @NotNull
    private CategoryType type;

    private String icon;

    private String color;
}
