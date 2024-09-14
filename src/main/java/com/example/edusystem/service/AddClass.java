package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.GroupClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddClass {
    @Autowired
    AccountDao accountDao;  //每个学生是一个Account的对象

    // 增：报名班课
    // 思路：查所有GroupClassType -> 添加
    List<GroupClassType> studentAddedClass = new ArrayList<>();



}



//查：这个学生报名的所有课程
// 增：报名班课


//查：学生个人信息



//查：所有报名的课程
//查：报名课程中的【已上课程】
//查：报名课程中的【未上课程】
//查：最近上课提醒
//查：所有班课种类和信息

//增：报名班课
//改：退选班课
//改：更新座位数
//改：更新剩余课时