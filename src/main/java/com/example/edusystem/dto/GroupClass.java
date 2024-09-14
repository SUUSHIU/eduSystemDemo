package com.example.edusystem.dto;

import org.springframework.lang.NonNull;

import java.util.List;

public class GroupClass {

    /**
     * 课程类的ID
     */
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
     * 存储参加该班课的所有accountId
     */
    public List<String> accountIdList;

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

}
