package com.example.oauth2.service.impl;

import com.example.oauth2.dto.MiniAppDto;
import com.example.oauth2.entity.SysAdmin;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MiniAppServiceImpl {

    private String password = new BCryptPasswordEncoder().encode("123456");

    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    public SysAdmin loadUserByOpenId(String code) {
        MiniAppDto openId = this.getOpenId(code);
        //TODO 根据miniapp 的oepnid查询数据库，如果有就返回数据库的，没有新增一个在返回

        return new SysAdmin().setId(2L)
                .setUsername("luok")
                .setPassword(password)
                .setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                .setNickName("luok")
                .setPhone("234");
    }

    private MiniAppDto getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxb629f2bbf5130171&secret=26a4d7e031afcce029f2092f491988b4&js_code=" + code + "&grant_type=authorization_code";
        MiniAppDto miniAppDto = this.restTemplate().getForEntity(url, MiniAppDto.class).getBody();
        System.out.println(miniAppDto);
        try {
            if (miniAppDto.getErrcode() != 0) {
                throw new Exception(miniAppDto.getErrmsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miniAppDto;
    }

}
