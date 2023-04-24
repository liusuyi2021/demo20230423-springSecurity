package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/level1/{id}")
    public String level1(@PathVariable("id") String id){
        return "/level1/"+id;
    }
    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable("id") String id){
        return "/level2/"+id;
    }
    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id") String id){
        return "/level3/"+id;
    }
}
