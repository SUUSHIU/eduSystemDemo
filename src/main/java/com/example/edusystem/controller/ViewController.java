package com.example.edusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    //写所有html页面
    //注册登录
    @GetMapping("/register")
    public String getRegister(){
        return "/register.html";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/login.html";
    }


    //查看课程
    @GetMapping("/myClassHomePage")
    public String getMyClassHomePage(){ return "/myClassHomePage.html"; }

    @GetMapping("/upComingClass")
    public String getUpComingClass(){ return "/upComingClass.html"; }

    @GetMapping("/recentClass")
    public String getRecentClass(){ return "/recentClass.html"; }

    @GetMapping("/finishedClass")
    public String getFinishedClass(){ return "/finishedClass.html"; }


    //选课
    @GetMapping("/groupClassType")
    public String getGroupClassType(){
        return "/groupClassType.html";
    }

    @GetMapping("/groupClassName")
    public String getGroupClassName(){
        return "/groupClassName.html";
    }

    @GetMapping("/baseGroupClassName")
    public String getBaseGroupClassName(){
        return "/baseGroupClassName.html";
    }



    //学生信息页（计算课时数）
    @GetMapping("/myPage")
    public String getMyPage(){
        return "/myPage.html";
    }



    //教务端- 设置课程，管理学生
    @GetMapping("/setGroupClass")
    public String getSetGroupClass(){
        return "/setGroupClass.html";
    }

}

