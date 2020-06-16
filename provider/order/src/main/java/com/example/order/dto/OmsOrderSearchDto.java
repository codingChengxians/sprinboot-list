package com.example.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.*;

@Data
public class OmsOrderSearchDto implements Serializable {
    private static final long serialVersionUID = -44598085465556961L;
    
    private String filter;
}