package com.example.edusystem.service;

import com.example.edusystem.dao.GroupClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupClassService {
    @Autowired
    GroupClassDao groupClassDao;

}


/*
studentNumber -> getAll baseClassName ;

①最近上课提醒(RecentClass remind)
如果同一天则显示两节课
List<String> recentClass = (LocalDateTime - baseClassName.classStartDate).sort() { return min; }
get Mode/DateOfWeek/StartTime/EndTime...

②未上课的课程（upcomingClass）
List<String> upcomingClass = new ArrayList<>();
    for()//过一遍所有课
        if(LocalDateTime < baseClassName.classStartDate) {
            upcomingClass.add(baseClassName);

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