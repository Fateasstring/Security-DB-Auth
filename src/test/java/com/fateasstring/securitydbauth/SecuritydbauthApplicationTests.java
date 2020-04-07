package com.fateasstring.securitydbauth;

import com.fateasstring.securitydbauth.bean.User;
import com.fateasstring.securitydbauth.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SecuritydbauthApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> getUser = (List<User>) userMapper.loadUserByUsername("root");
        System.out.println(getUser);

    }

}
