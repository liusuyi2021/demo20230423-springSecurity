package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DatabaseDriver.MYSQL;


/**
 * @ClassName: SecurityConfiguration
 * @Description:
 * @Author: 刘苏义
 * @Date: 2023年04月23日14:41
 * @Version: 1.0
 **/
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    DataSource dataSource;
    @Resource
    WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;
    @Resource
    WebAuthenticationFailureHandler webAuthenticationFailureHandler;
    /*JDBC验证*/

    /*内存验证*/
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user1 = User
                .withUsername("user1")
                .password(passwordEncoder.encode("123456"))
                .roles("vip1")
                .build();
        UserDetails user2 = User
                .withUsername("user2")
                .password(passwordEncoder.encode("123456"))
                .roles("vip2")
                .build();
        UserDetails user3 = User
                .withUsername("user3")
                .password(passwordEncoder.encode("123456"))
                .roles("vip3")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2", "vip3")
//                .and()
//                .withUser("liusuyi").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
//    }

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/js/*","/css/*").permitAll()
//                .antMatchers("/level1/*").hasRole("vip1")
//                .antMatchers("/level2/*").hasRole("vip2")
//                .antMatchers("/level3/*").hasRole("vip3");
//
//        http.formLogin()// 开启登录
//                .loginPage("/loginPage")   //自定义登录页面
//                .loginProcessingUrl("/doLogin")   // 登录访问路径
//                .defaultSuccessUrl("/hello").permitAll() // 登陆成功跳转
//                //.successHandler(webAuthenticationSuccessHandler)//适用于前后端分离返回前端登录成功
//                //.failureHandler(webAuthenticationFailureHandler)//适用于前后端分离返回前端登录失败
//                //.failureForwardUrl("/error")
//                 .failureUrl("/loginPage")
//                .and()
//                .csrf().disable() //关闭csrf防护
//                // 开启退出
//                .logout()
//                .logoutUrl("/logout") // 登出访问路径
//                .logoutSuccessUrl("/loginPage") // 登出成功跳转
//                .permitAll()
//                .and()
//                .rememberMe();
//    }


    /**
     * 配置过滤器链：注册SecurityFilterChain的一个实例
     * 对应： configure(HttpSecurity)
     * 对登录页面等进行配置
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/",                        //排除不需spring security验证的页面
                        "/js/**", "/css/**", "/jQuery/**", "/images/**", "/logo/**", "/semantic/**").permitAll()    //解决静态资源被拦截的问题(新，写在这里)
                .antMatchers("/level1/*").hasRole("vip1")
                .antMatchers("/level2/*").hasRole("vip2")
                .antMatchers("/level3/*").hasRole("vip3")
                .anyRequest().authenticated()
                .and()
                // 开启登录
                .formLogin()
                .loginPage("/loginPage")   //自定义登录页面
                .loginProcessingUrl("/doLogin")   // 登录访问路径
                .defaultSuccessUrl("/hello").permitAll() // 登陆成功跳转
                //.successHandler(webAuthenticationSuccessHandler)//适用于前后端分离返回前端登录成功
                //.failureHandler(webAuthenticationFailureHandler)//适用于前后端分离返回前端登录失败
                //.failureForwardUrl("/error")
                // .failureUrl("/fail")
                .and()
                .csrf().disable() //关闭csrf防护
                // 开启退出
                .logout()
                .logoutUrl("/logout") // 登出访问路径
                .logoutSuccessUrl("/hello") // 登出成功跳转
                .permitAll();

        return http.build();
    }

}
