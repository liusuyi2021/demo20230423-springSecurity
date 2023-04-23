package com.example.config;

/**
 * @ClassName: WebAuthenticationFailureHandler
 * @Description:
 * @Author: 刘苏义
 * @Date: 2023年04月23日16:29
 * @Version: 1.0
 **/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 认证失败
 */
@Component
public class WebAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg","登录失败");
        hashMap.put("e",e.getMessage());
        //spring提供的状态码
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String s = new ObjectMapper().writeValueAsString(hashMap);
        httpServletResponse.getWriter().println(s);
    }
}