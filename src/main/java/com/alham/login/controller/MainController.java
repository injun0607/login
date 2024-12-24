package com.alham.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MainController {


    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.access.exp}")
    private String accessExp;

    @Value("${jwt.refresh.exp}")
    private String refreshExp;


    @GetMapping("/main")
    @ResponseBody
    public String get(){
        System.out.println("key = " + key);
        System.out.println("accessExp = " + accessExp);
        System.out.println("refreshExp = " + refreshExp);


        return "main";
    }

    @GetMapping("/login/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/login/success")
    public String loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("1111111111111");
        return "loginSuccess";
    }


}
