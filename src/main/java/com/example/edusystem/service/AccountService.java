package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import com.example.edusystem.dto.CourseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

