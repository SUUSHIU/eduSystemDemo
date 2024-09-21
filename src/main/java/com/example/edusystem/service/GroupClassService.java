package com.example.edusystem.service;

import com.example.edusystem.dao.GroupClassDao;
import com.example.edusystem.dto.GroupClass;
import com.example.edusystem.dto.GroupClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GroupClassService {
    @Autowired
    GroupClassDao groupClassDao;

    @Autowired
    AccountService accountService;

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
        LocalDate now = LocalDate.now();

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
        LocalDate now = LocalDate.now();

        //过滤出日期比今天晚的，并返回所有课程
        return  allClassByStudent.stream()
                .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isAfter(now))
                .sorted(Comparator.comparing(GroupClass::getClassStartDate))
                .toList();

    }


    //【已结束的课程】列表
    public List<GroupClass> getFinishedClassByStudentNumber(String studentNumber) {
        List<GroupClass> allClassByStudent = getAllClassByStudent(studentNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();


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
    public List<GroupClass> getGroupClassNameByGroupClassType(GroupClassType groupClassType){
        List<GroupClass> allGroupClasses = groupClassDao.findAll();
        return allGroupClasses.stream()
                .filter(gc -> {
                    var groupClassType1 = gc.getGroupClassType();
                    return groupClassType1 != null && groupClassType1.equals(groupClassType);
                })
                .toList();
    }

    //第二层：通过查找Name获取相应的baseClassName
    public List<GroupClass> getBaseClassNameByGroupClassName(String className){
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

    public GroupClass getById(Long id){
        Optional<GroupClass> groupClass = groupClassDao.findById(id);
        return groupClass.orElse(null);
    }

    public boolean saveGroupClassById(String studentNumber, GroupClass groupClass){
        List<String> studentNumberList = groupClass.getStudentNumberList();
        studentNumberList.add(studentNumber);
        groupClass.setStudentNumberList(studentNumberList);
        if (groupClass.getTotalSeats() > 0){
            groupClass.setTotalSeats(groupClass.getTotalSeats() - 1);
            accountService.countAccountNumber(studentNumber, groupClass);
            groupClassDao.save(groupClass);
        } else
        {
            return false;
        }
        return true;
    }



}

