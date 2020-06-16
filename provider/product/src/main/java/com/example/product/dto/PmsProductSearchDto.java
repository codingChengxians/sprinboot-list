package com.example.product.dto;

import java.io.Serializable;
import lombok.*;

@Data
public class PmsProductSearchDto implements Serializable {
    private static final long serialVersionUID = 637078400398357775L;
    
    private String filter;
    private Long categoryId;
    private  Long brandId;
}