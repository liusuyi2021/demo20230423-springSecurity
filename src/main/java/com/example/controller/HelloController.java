package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: HelloController
 * @Description:
 * @Author: 刘苏义
 * @Date: 2023年04月23日14:43
 * @Version: 1.0
 **/
@Controller
public class HelloController {

    /*首页*/
    @RequestMapping("/hello")
    public String hello(){
        return "index";
    }
    /*登录页*/
    @RequestMapping("/loginPage")
    public String login(){
        return "login";
    }

}
