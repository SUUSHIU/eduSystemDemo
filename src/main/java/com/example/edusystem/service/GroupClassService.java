package com.example.edusystem.service;

import com.example.edusystem.dao.GroupClassDao;
import com.example.edusystem.dto.GroupClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class GroupClassService {
    @Autowired
    GroupClassDao groupClassDao;

    public List<GroupClass> getRecentClassByStudentNumber(String studentNumber){
        List<GroupClass> allCourseByStudent = getAllCourseByStudent(studentNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        // 过滤出今天的课程
        List<GroupClass> todayClasses = allCourseByStudent.stream()
                .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isEqual(now))
                .toList();
        // 如果今天有课程，返回今天的所有课程
        if (!todayClasses.isEmpty()) {
            return todayClasses;
        }
        // 如果今天没有课程，按日期升序排序，返回最近一天的所有课程
        LocalDate nearestDate = allCourseByStudent.stream()
                .map(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter))
                .filter(date -> date.isAfter(now))
                .min(LocalDate::compareTo)
                .orElse(null);

        if (nearestDate != null) {
            return allCourseByStudent.stream()
                    .filter(groupClass -> LocalDate.parse(groupClass.getClassStartDate(), formatter).isEqual(nearestDate))
                    .toList();
        }

        return List.of();
    }

    private List<GroupClass> getAllCourseByStudent(String studentNumber){
        return groupClassDao.findByStudentNumberIn(List.of(studentNumber));
    }

}


/*
SELECT
studentNumber -> getAll baseClassName ;

①最近上课提醒(RecentClass remind)
如果同一天则显示两节课
List<GroupClass> recentClass = (LocalDateTime - baseClassName.classStartDate).sort() { return min; }
get Mode/DateOfWeek/StartTime/EndTime...

②未上课的课程（upcomingClass）
List<String> upcomingClass = new ArrayList<>();
    for()//过一遍所有课
        if(LocalDateTime < baseClassName.classStartDate) {
            upcomingClass.add(baseClassName);
            }

③已结束的课程（finishedClass）
List<String> finishedClass = new ArrayList<>();
    if(LocalDateTime > baseClassName.classStartDate) {
        FinishedClass.add(baseClassName);
    }

 */


// 思路：查所有GroupClassType -> 添加
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