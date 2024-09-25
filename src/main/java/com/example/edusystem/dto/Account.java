package com.example.edusystem.dto;

import jakarta.persistence.*;

import java.util.Map;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "student_number", unique = true, nullable = false)
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
    @Column(name = "password", nullable = false)
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
    @Column(name = "name")
    public String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    /**
     * 报名课程区分
     */

    @Column(name = "Course_Category")
    public CourseCategory courseCategory;

    //已使用时间
    @ElementCollection
    public Map<String, Double> UsedClassHour;

    //未使用时间
    @ElementCollection
    public Map<String, Double> restClassHour;

    public CourseCategory getCourseCategory() {
        return courseCategory;
    }
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    /**
     * 课程开始时间 yyyy-mm
     */
    @Column(name = "Course_Start_Date")
    public String courseStartDate;

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String startDate) {
        this.courseStartDate = startDate;
    }

    public Account(String num, String password, String name, String courseStartDate, CourseCategory courseCategory){
        this.studentNumber = num;
        this.password = password;
        this.name = name;
        this.courseStartDate = courseStartDate;
        this.courseCategory = courseCategory;
    }
    public Account(){

    }
}
