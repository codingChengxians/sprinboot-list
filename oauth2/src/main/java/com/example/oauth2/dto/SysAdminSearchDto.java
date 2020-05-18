package com.example.oauth2.dto;

import java.util.Date;
import java.io.Serializable;
import lombok.*;

@Data
public class SysAdminSearchDto implements Serializable {
    private static final long serialVersionUID = 310790627431478664L;
    
    private String filter;
}