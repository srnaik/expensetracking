package com.sac.expensetracking.payload.response;

import com.sac.expensetracking.util.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryResponse {
    private String categoryName;

    @NotBlank
    private CategoryType type;

    private String icon;

    private String color;

    private UUID id;

    public CategoryResponse(String name, CategoryType type, String icon, String color, UUID id) {

        this.categoryName = name;
        this.icon = icon;
        this.color = color;
        this.type = type;
        this.id = id;
    }
}
