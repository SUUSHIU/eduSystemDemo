package com.example.edusystem.service;

import com.example.edusystem.dao.AccountDao;
import com.example.edusystem.dto.Account;
import com.example.edusystem.dto.CourseCategory;
import com.example.edusystem.dto.GroupClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    //注册
    public boolean checkRegister(String studentNumber, String password) {
        //如果注册信息任一为空，返回失败
        if (studentNumber.isEmpty() || password.isEmpty()) {
            return false;
        //如果注册信息已存在，返回失败
        }  else if (accountDao.existsByStudentNumber(studentNumber)){
            return false;
        //其余返回正确
        } else {
            return true;
        }
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

    //保存
    public void save(String studentNumber, String password, String name,
                     String courseStartDate, CourseCategory courseCategory) {
        Account account = new Account(studentNumber, password, name, courseStartDate, courseCategory);
        accountDao.save(account);
    }

    //删除所有
    public void deleteAll(){
        List<Account> all = accountDao.findAll();
        for(Account deleteAccount : all){
            accountDao.deleteById(deleteAccount.getId());
        }
    }

    //选课-加算课时
    public void countAccountNumber(String studentNumber, GroupClass groupClass){
        //初始课程总数
        double initialTotalClassNumber = groupClass.getCourseNumber();
        //本次所选课程的时长（End - Start）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(groupClass.getClassStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(groupClass.getClassStartTime(), formatter);
        Duration dur = Duration.between(startTime, endTime);
        double thisClassHour = dur.toMinutes() / 60.0;

        //每次扣除课时的记录 -> Map<String, Double> -> baseClassName，thisClassHour
        Account account = accountDao.findByStudentNumber(studentNumber);
        if (account.usedClassHour == null) { account.usedClassHour = new HashMap<>(); }

        account.usedClassHour.put(groupClass.baseClassName, thisClassHour);

        //总共使用的课时（value 的 sum）
        double UsedHourTotal =account.usedClassHour.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        //记录每次剩余课时的记录 -> Map<String, Double> -> baseClassName，restClassNumber
        double restClassNumber = initialTotalClassNumber - UsedHourTotal;
        if (account.restClassHour == null) { account.restClassHour = new HashMap<>();}
        account.restClassHour.put(groupClass.baseClassName, restClassNumber);

        // 更新
        accountDao.save(account);

    }



    //退课-减课时
    //这个方法名起得好怪
    public void minusCountAccountNumber(String studentNumber, GroupClass groupClass){
        //初始课程总数
        double initialTotalClassNumber = groupClass.getCourseNumber();
        //已使用课程数（所选课程的时长 Duration类）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(groupClass.getClassStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(groupClass.getClassStartTime(), formatter);
        Duration dur = Duration.between(startTime, endTime);
        double thisClassHour = dur.toMinutes() / 60.0;

        Account account = accountDao.findByStudentNumber(studentNumber);
        if (account.usedClassHour == null) { account.usedClassHour = new HashMap<>(); }
        account.usedClassHour.remove(groupClass.baseClassName, thisClassHour);

        double UsedHourTotal =account.usedClassHour.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        double restClassNumber = initialTotalClassNumber - UsedHourTotal;
        if (account.restClassHour == null) { account.restClassHour = new HashMap<>();}
        account.restClassHour.put(groupClass.baseClassName, restClassNumber);

        accountDao.save(account);
    }
}
