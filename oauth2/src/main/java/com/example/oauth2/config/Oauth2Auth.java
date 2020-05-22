package com.example.oauth2.config;

import com.example.oauth2.config.TokenGranter.CustomMiniAppTokenGrant;
import com.example.oauth2.config.TokenGranter.CustomSmsCodeAbstractToken;
import com.example.oauth2.entity.SysAdmin;
import com.example.oauth2.service.impl.MiniAppServiceImpl;
import com.example.oauth2.service.impl.SmsCodeServiceImpl;
import com.example.oauth2.service.impl.SysAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.DefaultToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.*;

/**
 * @author luok
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Auth extends AuthorizationServerConfigurerAdapter {
    @Autowired

    private SysAdminServiceImpl userDetailsService;
    @Autowired
    private SmsCodeServiceImpl smsCodeService;
    @Autowired
    private MiniAppServiceImpl miniAppService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//表单申请令牌
        security.allowFormAuthenticationForClients()
                //url:/oauth/check_token allow check token 获取check token
//                .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                // 获取密钥需要身份认证，使用单点登录时必须配置  判断token 账户登录  token key
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientId")
                .secret(bCryptPasswordEncoder.encode("123456"))
                //类型
                .authorizedGrantTypes("authorization_code", "password", "refresh_token", "sms_code","mini_app")
                // 或调地址
                .redirectUris("http://www.baidu.com")
                //配置申请的权限范围
                .scopes("all")
                //登录的时候自动获取权限，不用选择了
                .autoApprove(true)
                //token到期时间
                .accessTokenValiditySeconds(60)
                //刷新token 的时间
                .refreshTokenValiditySeconds(9000);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //获取edpoints中的四个参数，把tokenGrants给重写了，因为这个没有add添加方法，所以手动写需要的tokenGrant，再用那个帮助类给添加进去，CompositeTokenGranter
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(endpoints.getClientDetailsService(), endpoints.getTokenServices(), endpoints.getOAuth2RequestFactory(), endpoints.getAuthorizationCodeServices());
        //把六个类tokengranter放进去
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
        //把jwt转换和token增强放一起，分开放入会只有增强没有转换 源码 if (this.tokenEnhancer == null && this.accessTokenConverter() instanceof JwtAccessTokenConverter) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        //配置manager，才能使用密码模式
        endpoints.authenticationManager(authenticationManager)
                //密码模式查询的service
                .userDetailsService(userDetailsService)
                //redis存储，value有问好乱码，需要解决
                .tokenStore(tokenStore())
                //token转jwt
//                .accessTokenConverter(jwtAccessTokenConverter())
                //token加强，放入id之类的自己需要的信息 tokenEnhancer和accessTokenConverter会只有增强，不会生成jwt还是会使用uuid 的access_token  jwt转换只有在增强为null 的时候。
                //所以需要使用tokenEnhancerChain 测试结果是两个都放在了tokenEnhancerChain中，.accessTokenConverter(jwtAccessTokenConverter())可以不写也一样
                .tokenEnhancer(tokenEnhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //redis存储创建的时候需要写入账号密码ip和连接池之类的
    //@Bean
    //public TokenStore redisTokenStore() {
    //    return new RedisTokenStore(redisConnectionFactory);};


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称加密
        converter.setSigningKey("JWTKEY");
        //非对称加密，jdk中有个keytool工具生成的
        //KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "123456".toCharArray());
        //converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2"));
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (oAuth2AccessToken, oAuth2Authentication) -> {
            Map<String, Object> additionalInformation = new HashMap<>(16);
            System.out.println(oAuth2Authentication.toString());
            SysAdmin user = (SysAdmin) oAuth2Authentication.getPrincipal();
            additionalInformation.put("id", user.getId().toString());
            additionalInformation.put("username", user.getUsername());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
            return oAuth2AccessToken;
        };

    }

    private List<TokenGranter> getDefaultTokenGranters(ClientDetailsService clientDetails, AuthorizationServerTokenServices tokenServices,
                                                       OAuth2RequestFactory requestFactory, AuthorizationCodeServices authorizationCodeServices) {
        //重写的TokenGranters 添加拓展类，openid 手机验证码之类的拓展
        List<TokenGranter> tokenGranters = new ArrayList();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (this.authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(this.authenticationManager, tokenServices, clientDetails, requestFactory));
        }
        tokenGranters.add(new CustomSmsCodeAbstractToken(tokenServices, clientDetails, requestFactory, smsCodeService));
        tokenGranters.add(new CustomMiniAppTokenGrant(tokenServices, clientDetails, requestFactory, miniAppService));


        return tokenGranters;
    }
}
