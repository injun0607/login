package com.alham.login.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class MainController {


    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.access.exp}")
    private String accessExp;

    @Value("${jwt.refresh.exp}")
    private String refreshExp;


    @GetMapping("/main")
    public String get(){
        System.out.println("key = " + key);
        System.out.println("accessExp = " + accessExp);
        System.out.println("refreshExp = " + refreshExp);


        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
