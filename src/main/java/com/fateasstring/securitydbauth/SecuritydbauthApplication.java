package com.fateasstring.securitydbauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fateasstring.securitydbauth.mapper")
public class SecuritydbauthApplication {

    public static void main(String[] args) {

        SpringApplication.run(SecuritydbauthApplication.class, args);
    }

}
