package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import com.example.edusystem.dto.CourseCategory;
import com.example.edusystem.dto.GroupClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao; //实例化dao层接口

    //功能
    //注册--登录
    public boolean checkRegister(String name, String password){
        if(name.isEmpty() || password.isEmpty()){ return false; }

        List<Account> accountList = accountDao.findAll(); //?这是一张List吗？
        for (Account account : accountList){
            if(account.getLoginName().equals(name)) { return false; }
        }
        return true;
    }

    public void save(String name,String password){
        Account account = new Account();
        account.setLoginName(name);
        account.setPassword(password);
        accountDao.save(account); //这个方法是干嘛的？
    }

    public boolean checkLogin(String name, String password){
        Account account = accountDao.findByLoginName(name); //这为什么不是一张List？
        if(account == null){
            return false;
        } else {
            account.getPassword().equals(password);
            return true;
           // String passwordByFind = account.getPassword(); 原来的写法，现在的写法有什么不同吗？
           // return password.equals(passwordByFind);
        }
    }









}
