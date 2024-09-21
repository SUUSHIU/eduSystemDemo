package com.example.edusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/register")
    public String getRegister(){
        return "/register.html";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "/login.html";
    }
    @GetMapping("/myPage")
    public String getMyPage(){
        return "/myPage.html";
    }
    @GetMapping("/setGroupClass")
    public String getSetGroupClass(){
        return "/setGroupClass.html";
    }
    @GetMapping("/AllGroupClass")
    public String getAllGroupClass(){
        return "/AllGroupClass.html";
    }
}

