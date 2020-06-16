package com.example.product.dto;

import java.io.Serializable;
import lombok.*;

@Data
public class PmsBrandSearchDto implements Serializable {
    private static final long serialVersionUID = -87921431909203707L;
    
    private String filter;
}