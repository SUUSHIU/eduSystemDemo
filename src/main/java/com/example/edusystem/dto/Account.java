package com.example.edusystem.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    /**
     * 用户类的ID
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 学号
     * 唯一，用来登录
     */
    private String studentNumber;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }


    /**
     * 登录密码
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 学生真实姓名
     */
    public String name;


    /**
     * 报名课程区分
     */
    public CourseCategory courseCategory;


    /**
     * 课程开始时间 yyyy-mm
     */
    public String courseStartDate;
}
