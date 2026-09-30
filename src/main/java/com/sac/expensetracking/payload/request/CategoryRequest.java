package com.sac.expensetracking.payload.request;

public class CategoryRequest {

    private String categoryName;

    private String type;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
