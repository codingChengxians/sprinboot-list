package com.example.category.dto;

import java.io.Serializable;
import lombok.*;

@Data
public class PmsProductCategorySearchDto implements Serializable {
    private static final long serialVersionUID = 395160629206030433L;
    
    private String filter;
}