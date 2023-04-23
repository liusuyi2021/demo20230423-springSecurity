package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SecurityConfiguration
 * @Description:
 * @Author: 刘苏义
 * @Date: 2023年04月23日14:41
 * @Version: 1.0
 **/
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Resource
    WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;
    @Resource
    WebAuthenticationFailureHandler webAuthenticationFailureHandler;
    // 注册UserDetailsService的一个实例
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        users.createUser(User.withUsername("root").password("{noop}123456").roles("admin").build());
        users.createUser(User.withUsername("cjn").password("{noop}123456").roles("user").build());
        return users;
    }
    /**
     * 配置过滤器链：注册SecurityFilterChain的一个实例
     * 对应： configure(HttpSecurity)
     * 对登录页面等进行配置
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(  "/login",                        //排除不需spring security验证的页面
                        "/js/**", "/css/**", "/jQuery/**", "/images/**", "/icon/**", "/file/**").permitAll()    //解决静态资源被拦截的问题(新，写在这里)
                .anyRequest().authenticated()
                .and()
        // 开启登录
        .formLogin()
                .loginPage("/loginPage")   //自定义登录页面
                .loginProcessingUrl("/doLogin")   // 登录访问路径
                .defaultSuccessUrl("/hello").permitAll() // 登陆成功跳转
                .successHandler(webAuthenticationSuccessHandler)
                .failureHandler(webAuthenticationFailureHandler)
                //.failureForwardUrl("/error")
                .failureUrl("/fail")
                .and()
                .csrf().disable() //关闭csrf防护
        // 开启退出
        .logout()
                .logoutUrl("/logout") // 登出访问路径
                .logoutSuccessUrl("/login") // 登出成功跳转
                .permitAll();

        return http.build();
    }

}
