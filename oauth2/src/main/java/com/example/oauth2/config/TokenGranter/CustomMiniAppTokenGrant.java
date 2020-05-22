package com.example.oauth2.config.TokenGranter;

import com.example.oauth2.entity.SysAdmin;
import com.example.oauth2.service.impl.MiniAppServiceImpl;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luok
 */
public class CustomMiniAppTokenGrant extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "mini_app";
    private MiniAppServiceImpl miniAppService;
    private OAuth2RequestFactory requestFactory;

    public CustomMiniAppTokenGrant(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                   OAuth2RequestFactory requestFactory, MiniAppServiceImpl miniAppService) {
        this(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.miniAppService = miniAppService;
        this.requestFactory = requestFactory;

    }


    protected CustomMiniAppTokenGrant(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String code = parameters.get("code");
        SysAdmin userDetail = this.getUserDetail(code);
        OAuth2Request storedOAuth2Request = this.requestFactory.createOAuth2Request(client, tokenRequest);
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(userDetail, userDetail.getUsername(), userDetail.getAuthorities());
        authenticationToken.setDetails(userDetail);
        return new OAuth2Authentication(storedOAuth2Request, authenticationToken);

    }

    private SysAdmin getUserDetail(String code) {
        return miniAppService.loadUserByOpenId(code);
    }
}
