package com.example.edusystem.controller;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import com.example.edusystem.dto.CourseCategory;
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
        String studentNumber = user.get("studentNumber");
        String password = user.get("password");
        String name = user.get("name");
        String courseStartDate = user.get("courseStartDate");
        String courseCategoryValue = user.get("courseCategory");
        CourseCategory courseCategory = CourseCategory.getByValue(courseCategoryValue);


        Map<String, Object> response = new HashMap<>(); //检查注册是否正确
        boolean result = accountService.checkRegister(studentNumber, password);

        if (result) {
            accountService.save(studentNumber, password, name, courseStartDate, courseCategory );
            response.put("success", true); //注册成功
        } else {
            response.put("success", false); //表示注册失败
            response.put("message", "注册失败"); //向前端传输失败信息
        }
        return ResponseEntity.ok(response); //返回JSON响应
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> user) {
        String studentNumber = user.get("studentNumber");
        String password = user.get("password");

        Map<String, Object> response = new HashMap<>(); //检查登录是否正确
        boolean result = accountService.checkLogin(studentNumber, password);

        if (result) {
            response.put("Success", true);
            response.put("redirectUrl", "/myPage.html"); //成功登录，定向到myPage
        } else {
            response.put("Success", false);
            response.put("redirectUrl", "/login.html"); //登录失败，定向到login
        }
        return ResponseEntity.ok(response); //返回JSON响应
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        // 处理注册逻辑
        return ResponseEntity.ok("注册成功");
    }


}

