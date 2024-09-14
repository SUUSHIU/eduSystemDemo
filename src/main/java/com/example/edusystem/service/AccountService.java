package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    //功能
    //注册--登录
    public boolean checkRegister(String name, String password) {
        if (name.isEmpty() || password.isEmpty()) {
            return false;
        }

        List<Account> accountList = accountDao.findAll();
        for (Account account : accountList) {
            if (account.getStudentNumber().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public void save(String name, String password) {
        Account account = new Account();
        account.setStudentNumber(name);
        account.setPassword(password);
        accountDao.save(account);
    }

    public boolean checkLogin(String name, String password) {
        Account account = accountDao.findByStudentNumber(name);
        if (account == null) {
            return false;
        } else {
            account.getPassword().equals(password);
            return true;
        }
    }
}
