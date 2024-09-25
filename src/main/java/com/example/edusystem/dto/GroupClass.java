package com.example.edusystem.dto;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
public class GroupClass {

    /**
     * 课程类的ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * 报名课程区分
     * 该字段表示课程的类别或分类
     */
    @NonNull
    //不能为空
    public CourseCategory courseCategory;

    /**
     * 班课种类
     * 第一层：表示该班课的类型（5种），小论文，研究计划书，面试，排版，制本
     */
    public GroupClassType groupClassType;

    /**
     * 班课回数名
     * 第二层：如小论文课中的第一回，第二回
     */
    public String className;

    /**
     * 班课的班级
     * 第二层（最小单位）：小论文中的设计班，纯艺班
     */
    public String baseClassName;

    /**
     * 上课形式（2种）
     * true 表示线上，false 表示线下
     */
    public boolean classMode;

    /**
     * 上课日期
     */
    public String classStartDate;

    /**
     * 上课曜日
     */
    public String classStartDateOfWeek;

    /**
     * 上课时间
     * 表示班课开始的具体时间（如：09:00）
     */
    public String classStartTime;

    /**
     * 下课时间
     * 表示班课结束的具体时间（如：11:00）
     */
    public String classEndTime;

    /**
     * 授课语言
     */
    public String classLanguage;

    /**
     * 报名班课的学生名单
     * 存储参加该班课的所有studentNumber
     * 使用基础集合的注解
     */
    public List<String> studentNumberList;

    /**
     * 定员数
     * 表示该班课的最大报名人数
     */
    public int totalSeats;


    /**
     * 初始课时
     * 根据courseCategory分配
     */
    public double courseNumber;

    /**
     * 已使用课时
     * 根据报名的baseClassName更新
     */
    public double usedClassNum;

    /**
     * 剩余课时
     * 初始课时-已使用课时
     */
    public double availableCourseNum;

//    public Long getId() { return id;}
//    public void setId(Long id) { this.id = id;}

    @NonNull
    public CourseCategory getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(@NonNull CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    public GroupClassType getGroupClassType() {
        return groupClassType;
    }

    public void setGroupClassType(GroupClassType groupClassType) {
        this.groupClassType = groupClassType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBaseClassName() {
        return baseClassName;
    }

    public void setBaseClassName(String baseClassName) {
        this.baseClassName = baseClassName;
    }

    public boolean isClassMode() {
        return classMode;
    }

    public void setClassMode(boolean classMode) {
        this.classMode = classMode;
    }

    public String getClassStartDate() { return classStartDate; }

    public void setClassStartDate(String classStartDate) {
        this.classStartDate = classStartDate;
    }

    public String getClassStartDateOfWeek() {
        return classStartDateOfWeek;
    }

    public void setClassStartDateOfWeek(String classStartDateOfWeek) {
        this.classStartDateOfWeek = classStartDateOfWeek;
    }

    public String getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(String classStartTime) {
        this.classStartTime = classStartTime;
    }

    public String getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(String classEndTime) {
        this.classEndTime = classEndTime;
    }

    public String getClassLanguage() {
        return classLanguage;
    }

    public void setClassLanguage(String classLanguage) {
        this.classLanguage = classLanguage;
    }

    //每个课的学生列表
    public List<String> getStudentNumberList() {
        return studentNumberList;
    }

    public void setStudentNumberList(List<String> studentNumberList) {
        this.studentNumberList = studentNumberList;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(double courseNumber) {
        this.courseNumber = courseNumber;
    }

    public double getUsedClassNum() {
        return usedClassNum;
    }

    public void setUsedClassNum(double usedClassNum) {
        this.usedClassNum = usedClassNum;
    }

    public double getAvailableCourseNum() {
        return availableCourseNum;
    }

    public void setAvailableCourseNum(double availableCourseNum) {
        this.availableCourseNum = availableCourseNum;
    }

}
