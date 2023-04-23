package com.example.config;

/**
 * @ClassName: WebAuthenticationSuccessHandler
 * @Description:
 * @Author: 刘苏义
 * @Date: 2023年04月23日16:27
 * @Version: 1.0
 **/
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 成功返回处理
 */
@Component
public class WebAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg","登录成功");
        hashMap.put("authentication",authentication);
        System.out.println(authentication.getDetails());
        //spring提供的状态码
        httpServletResponse.setStatus(HttpStatus.OK.value());
        String s = new ObjectMapper().writeValueAsString(hashMap);
        httpServletResponse.getWriter().println(s);
    }
}
