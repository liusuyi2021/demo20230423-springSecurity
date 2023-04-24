package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Demo20230423SpringSecurityApplicationTests {

    @Test
    public void contextLoads() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password="123456";
        String encoder="{bcrypt}$2a$10$keNhzaHRtqrLnrWW86F0/e5g9ezYsvBFYxe4MH94OW8C05GS757sm";
        boolean bool = passwordEncoder.matches(password, encoder);
        System.out.println(encoder + "：是否匹配？" + bool);
    }

    @Test
    public void test1() {
        String password = "123456";// 密码
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        for (int i = 1; i <= 5; i++) {
            // 加密明文密码，返回密文
            String encoder = passwordEncoder.encode(password);
            // 明文和密文进行匹配
            boolean bool = passwordEncoder.matches(password, encoder);
            System.out.println(encoder + "：是否匹配？" + bool);
        }
    }
}
