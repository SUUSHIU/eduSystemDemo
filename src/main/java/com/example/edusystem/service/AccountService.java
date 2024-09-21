package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import com.example.edusystem.dto.CourseCategory;
import com.example.edusystem.dto.GroupClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    //注册
    public boolean checkRegister(String studentNumber, String password) {
        if (studentNumber.isEmpty() || password.isEmpty()) {
            return false; //如果注册信息为空，返回失败
        }  else if (accountDao.existsByStudentNumber(studentNumber)){
            return false; // 如果注册信息已存在，返回失败
        } else {
            return true;
        }
    }
    //保存
    public void save(String studentNumber, String password, String name, String courseStartDate, CourseCategory courseCategory) {
        Account account = new Account(studentNumber, password, name, courseStartDate, courseCategory);
        accountDao.save(account);
// 删除所有数据
//        List<Account> all = accountDao.findAll();
//        for (Account account1 : all) {
//            accountDao.deleteById(account1.getId());
//        }
    }

    //登录
    public boolean checkLogin(String studentNumber, String password) {
        Account account = accountDao.findByStudentNumber(studentNumber);
        if (account == null) {
            return false;
        } else {
            account.getPassword().equals(password);
            return true;
        }
    }

    //计算课时
    public void countAccountNumber(String studentNumber, GroupClass groupClass){
        double baseTotalNumber = groupClass.getCourseNumber(); //初始课程总数
        double costNumber = Integer.valueOf(groupClass.getClassEndTime()) - Integer.valueOf(groupClass.getClassStartTime()); //选的这节课的课时

        Account account = accountDao.findByStudentNumber(studentNumber);

        // 获取现有的 courseUsedTime，如果不存在则创建一个新的 HashMap
        Map<String, Double> courseUsedTime = account.courseUsedTime;
        if (courseUsedTime == null) {
            courseUsedTime = new HashMap<>();
        }

        // 累加已使用的时间
        courseUsedTime.put(groupClass.baseClassName,
                courseUsedTime.getOrDefault(groupClass.baseClassName, 0.0) + costNumber);

        // 计算剩余时间：总时间 - 累加的已使用时间
        double restNumber = baseTotalNumber - courseUsedTime.get(groupClass.baseClassName);

        // 更新 account 中的已使用时间和剩余时间
        account.courseUsedTime = courseUsedTime;

        Map<String, Double> courseUnUsedTime = account.courseUnUsedTime;
        if (courseUnUsedTime == null) {
            courseUnUsedTime = new HashMap<>();
        }

        // 更新剩余时间
        courseUnUsedTime.put(groupClass.baseClassName, restNumber);
        account.courseUnUsedTime = courseUnUsedTime;

        accountDao.save(account);


        //退选课
        //Post（student， id）
        //通过课id 获得List -> 删除学生的Number， 座位数 + 1
        //通过学生的Number -> 回滚课时数
        //更新课时的功能（正好相反）

    }
}

