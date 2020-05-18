package com.example.oauth2.service.impl;

import com.example.oauth2.entity.SysAdmin;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsCodeServiceImpl {
    private List<SysAdmin> users;

    @PostConstruct
    public void initData() {
        users = new ArrayList<>(4);
        String password = new BCryptPasswordEncoder().encode("123456");
        users.add(new SysAdmin().setId(1L)
                .setUsername("admin")
                .setPassword(password)
                .setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("client"))
                .setNickName("laowang")
                .setPhone("123"));
        users.add(new SysAdmin().setId(2L)
                .setUsername("luok")
                .setPassword(password)
                .setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                .setNickName("luok")
                .setPhone("234"));
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        List<SysAdmin> collect = users.stream().filter(user -> phone.equals(user.getPhone())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            return collect.get(0);
        }
        throw new UsernameNotFoundException("帐号或密码错误！");
    }
}
