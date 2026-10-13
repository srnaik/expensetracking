package com.sac.expensetracking.payload.request;

import com.sac.expensetracking.util.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {

    private String categoryName;
    private CategoryType type;
    private String color;
    private String icon;
}
