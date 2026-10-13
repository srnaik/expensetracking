package com.sac.expensetracking.payload.response;

import com.sac.expensetracking.util.CategoryType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
