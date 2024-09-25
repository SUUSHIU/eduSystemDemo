package com.example.edusystem.service;

import com.example.edusystem.dao.GroupClassDao;
import com.example.edusystem.dto.GroupClass;
import com.example.edusystem.dto.GroupClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDate.now;

@Service
public class GroupClassService {
    @Autowired
    GroupClassDao groupClassDao;

    @Autowired
    AccountService accountService;

    //有点不懂这个方法的意图？
    //传入一节groupClass，看它的List中有没有这个studentNumber，有的话进行课时计算，并更新致account？
    public void saveGroupClass(GroupClass groupClass) {
        for (String studentNumber : groupClass.getStudentNumberList()) {
            accountService.countAccountNumber(studentNumber, groupClass);
        }
        groupClassDao.save(groupClass);
    }

    //功能1
    //最近上课提醒
    public List<GroupClass> getRecentClassByStudentNumber(String studentNumber) {
        List<GroupClass> allClassByStudent = getAllClassByStudent(studentNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = now();

        //过滤出今天的课程
        //stream 通常用来排序
        List<GroupClass> todayClasses = allClassByStudent.stream()
                .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isEqual(now))
                .toList();
        // 如果今天有课程，返回今天的所有课程
        if (!todayClasses.isEmpty()) {
            return todayClasses;
        }
        // 如果之后都没有课程，返回空
        LocalDate nearestDate = allClassByStudent.stream()
                //把课程开始时间统一成formatter的格式
                .map(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter))
                .filter(date -> date.isAfter(now))
                .min(LocalDate::compareTo)
                .orElse(null);

        // 如果今天没有课程，按日期升序排序，返回最近一天的所有课程
        if (nearestDate != null) {
            return allClassByStudent.stream()
                    .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isEqual(nearestDate))
                    .toList();
        }
        return List.of();
    }


    //【未上课的课程】列表
    public List<GroupClass> getUpComingClassByStudentNumber(String studentNumber) {
        List<GroupClass> allClassByStudent = getAllClassByStudent(studentNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = now();

        //过滤出日期比今天晚的，并返回所有课程
        return allClassByStudent.stream()
                .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isAfter(now))
                .sorted(Comparator.comparing(GroupClass::getClassStartDate))
                .toList();

    }


    //【已结束的课程】列表
    public List<GroupClass> getFinishedClassByStudentNumber(String studentNumber) {
        List<GroupClass> allClassByStudent = getAllClassByStudent(studentNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = now();


        return allClassByStudent.stream()  //直接返回整个值
                .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isBefore(now))
                .sorted(Comparator.comparing(GroupClass::getClassStartDate))
                .toList();
    }

    //这个学生选的所有课
    public List<GroupClass> getAllClassByStudent(String studentNumber) {
        List<GroupClass> allGroupClasses = groupClassDao.findAll();
        //stream专门用来排序
        return allGroupClasses.stream()
                .filter(gc -> {
                    //查在所有GroupClass中有这个studentNumber的，返回一个课程的List
                    List<String> studentList = gc.getStudentNumberList();
                    return studentList != null && studentList.contains(studentNumber);
                })
                .toList();
    }

    //第一层：通过查找Type获取相应的班课回数Name
    public List<GroupClass> getGroupClassNameByGroupClassType(GroupClassType groupClassType) {
        List<GroupClass> allGroupClasses = groupClassDao.findAll();
        return allGroupClasses.stream()
                .filter(gc -> {
                    var groupClassType1 = gc.getGroupClassType();
                    return groupClassType1 != null && groupClassType1.equals(groupClassType);
                })
                .toList();
    }

    //第二层：通过查找Name获取相应的baseClassName
    public List<GroupClass> getBaseClassNameByGroupClassName(String className) {
        //查一遍全部课
        List<GroupClass> allGroupClasses = groupClassDao.findAll();
        return allGroupClasses.stream()
                .filter(gc -> {
                    //查在所有GroupClass中属于这个的，返回一个base课程的List
                    var className1 = gc.getClassName();
                    return className1 != null && className1.equals(className);
                })
                .toList();
    }

    public GroupClass getById(Long id) {
        Optional<GroupClass> groupClass = groupClassDao.findById(id);
        //返回一个对象或null
        return groupClass.orElse(null);
    }

    //选课
    public boolean saveGroupClassById(String studentNumber, GroupClass groupClass) {
        //选课条件① 在课程开始前
        //选课条件② 座位数 > 0
        LocalDate now = now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate classStartDate = LocalDate.parse(groupClass.getClassStartDate(), formatter);
        List<String> studentNumberList = groupClass.getStudentNumberList();

        if (groupClass.getTotalSeats() > 0 && classStartDate.isAfter(now)) {
            //加入 studentNumber 到 List
            studentNumberList.add(studentNumber);
            groupClass.setStudentNumberList(studentNumberList);
            //座位数 - 1
            groupClass.setTotalSeats(groupClass.getTotalSeats() - 1);
            //更新学生的课时数
            accountService.countAccountNumber(studentNumber, groupClass);
            //保存
            groupClassDao.save(groupClass);
            return true;
        } else {
            return false;
        }
    }

    //退选课
    public boolean dropClassById(String studentNumber, GroupClass groupClass) {
        //退课条件① 在课程开始前
        LocalDate now = now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate classStartDate = LocalDate.parse(groupClass.getClassStartDate(), formatter);
        List<String> studentNumberList = groupClass.getStudentNumberList();

        if (classStartDate.isAfter(now)) {
            //将 studentNumber 从 List 中移除
            studentNumberList.remove(studentNumber);
            groupClass.setStudentNumberList(studentNumberList);
            //座位数 + 1
            groupClass.setTotalSeats(groupClass.getTotalSeats() + 1);
            //更新学生课时数
            accountService.minusCountAccountNumber(studentNumber, groupClass);
            //保存
            groupClassDao.save(groupClass);
            return true;
        } else {
            return false;
        }
    }
}

    //Post（student， id）
    //通过课id 获得List -> 删除学生的Number， 座位数 + 1
    //通过学生的Number -> 回滚课时数
    //更新课时的功能（正好相反）





