package com.example.oauth2.config.TokenGranter;

import com.example.oauth2.service.impl.SmsCodeServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomSmsCodeAbstractToken extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "sms_code";
    private SmsCodeServiceImpl userDetailsService;
    private OAuth2RequestFactory requestFactory;

    public CustomSmsCodeAbstractToken(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, SmsCodeServiceImpl smsCodeService) {
        this(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = smsCodeService;

    }

    protected CustomSmsCodeAbstractToken(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.requestFactory = requestFactory;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String phone = (String) parameters.get("phone");
        String smsCode = (String) parameters.get("smsCode");
        parameters.remove("smsCode");
        UserDetails userDetail = this.getUserDetail(phone, smsCode);
        OAuth2Request storedOAuth2Request = this.requestFactory.createOAuth2Request(client, tokenRequest);
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        authentication.setDetails(userDetail);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);
        return oAuth2Authentication;
    }

    private UserDetails getUserDetail(String phone, String smsCode) {
        //redis 查询code验证成功了再去获取用户信息，没有就报错验证码失败
        UserDetails userDetails = userDetailsService.loadUserByPhone(phone);

        return userDetails;
    }
}
