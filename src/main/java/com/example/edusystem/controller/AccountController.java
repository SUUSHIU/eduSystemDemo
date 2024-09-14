package com.example.edusystem.controller;

import com.example.edusystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> user) {
        String name = user.get("name");
        String password = user.get("password");
        Map<String, Object> response = new HashMap<>();
        boolean result = accountService.checkRegister(name, password);

        if (result) {
            accountService.save(name, password);
            response.put("Success", true);
        } else {
            response.put("Success", false); //什么意思？
            response.put("message", result); //什么意思？
        }
        return ResponseEntity.ok(response); //返回JSON响应
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> user) {
        String name = user.get("name");
        String password = user.get("password");
        Map<String, Object> response = new HashMap<>();
        boolean result = accountService.checkLogin(name, password);

        if (result) {
            response.put("Success", true);
            response.put("redirectUrl", "/home.html"); //重定向到home页面？？
        } else {
            response.put("Success", false);
            response.put("redirectUrl", "/failure.html"); //登录失败定向到failure页面？？
        }
        return ResponseEntity.ok(response); //返回JSON响应
    }
}








