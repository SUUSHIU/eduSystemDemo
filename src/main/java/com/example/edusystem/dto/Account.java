package com.example.edusystem.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    //注册账号
    private String loginName; //用户名（唯一，可不同于真实姓名）
    private String password; //登录密码
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLoginName() { return loginName; }
    public void setLoginName(String loginName) { this.loginName = loginName; }



    //个人信息页面
    public String name; //学生真实姓名
    public String studentId; //学号
    public CourseCategory courseCategory; //报名课程区分
    public double courseNumber;//初始课时
    public double usedClassNum; //已使用课时
    public double availableCourseNum;//剩余课时
    public String courseStartDate; //课程开始时间 yyyy-mm


    //选班课页面
    public GroupClassType groupClassType;//班课种类
    public String classStartDate; //上课日期
    public String classStartDateOfWeek; //上课曜日
    public String classStartTime; //上课时间
    public String classEndTime; //下课时间
    public String classMode;//上课形式（线上・线下）
    public String classNum; //第几回课（一般①～⑤）

    public List<String> mumberList; //报名班课的学生名单
    public int totalSeats; //定员数
    public int availableSeats; //剩余座位数
    public String classLanguage; //授课语言


    //我的课表页面
    public List<String> completedClassList; //已结束课程的列表
    public List<String> upcomingClassList; //未开始课程的列表
    public List<String> RecentClassList; //最近课程列表（只保留一个）
















}
