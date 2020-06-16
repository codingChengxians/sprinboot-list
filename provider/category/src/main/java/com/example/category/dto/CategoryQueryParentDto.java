package com.example.category.dto;

import lombok.Data;

@Data
public class CategoryQueryParentDto {
    private Long parentId;
    private int level;
}
