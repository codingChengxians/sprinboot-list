package com.example.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiniAppDto {
    private String expires_in;
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;
}
