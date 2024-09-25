package com.example.edusystem.controller;

import com.example.edusystem.dto.CourseCategory;
import com.example.edusystem.dto.GroupClass;
import com.example.edusystem.dto.GroupClassType;
import com.example.edusystem.service.GroupClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*写入数据库 -> PostMapping
从数据库拿出 -> GetMapping
每个人都能看到的 -> GroupClass
只有自己看到的 -> account
 */

@RestController
@RequestMapping("/api")
public class GroupClassController {

    @Autowired
    GroupClassService groupClassService;

    //前端页面：myClassHomePage -> 写死， 点击“最近上课提醒”后转跳新页面“recentClass”
    //最近课程提醒
    @GetMapping("/class/recent/{studentNumber}")
    public List<GroupClass> getRecentClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getRecentClassByStudentNumber(studentNumber);
    }

    //已结束的课程
    @GetMapping("/class/finish/{studentNumber}")
    public List<GroupClass> getFinishClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getFinishedClassByStudentNumber(studentNumber);
    }

    //未上课的课程
    @GetMapping("/class/upComing/{studentNumber}")
    public List<GroupClass> getUpComingClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getUpComingClassByStudentNumber(studentNumber);
    }


    //学生端选课：groupClassType -> 写死， 点击“某一课程”，跳转出属于它的全部className， 再点击跳转 baseClassName
    //className 与 baseClassName 属于同层，只是前端展示方式有折叠

    //获取所有班课回数（classType -> className）
    @GetMapping("/class/chooseClass/{value}")
    public List<GroupClass> getGroupClassNameByGroupClassType(@PathVariable("value") String value) {
        GroupClassType courseCategory = GroupClassType.getByValue(value);
        return groupClassService.getGroupClassNameByGroupClassType(courseCategory);
    }

    //获取所有时间点的班课 className -> baseClassName
    @GetMapping("/class/chooseClass/{name}")
    public List<GroupClass> getGroupClassNameByGroupClassTypeAndName(@PathVariable("name") String name) {
        return groupClassService.getBaseClassNameByGroupClassName(name);
    }

    //获得课程的id 和学号 studentNumber -> 把学号写进对象的studentNumberList中
    //同时座位 - 1
    @PostMapping("/chooseClass")
    public ResponseEntity<Map<String, Object>> chooseClass(@RequestBody Map<String, String> user) {
        //前端只能传String值
        //Map（String， String）-> 前端传入一个String，对应后端一个String
        //将前端的String值 -> 查 id 拿到相应对象
        String studentNumber = user.get("studentNumber");
        Long id = Long.parseLong(user.get("id"));
        GroupClass result = groupClassService.getById(id);

        //saveGroupClass方法中 studentNumber存进List，seat更新，classHour更新
        boolean b = false;
        if (result != null) {
            b = groupClassService.saveGroupClassById(studentNumber, result);
        }
        Map<String, Object> response = new HashMap<>();
        if (b) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response); //返回给前端
    }



    //为什么这边需要有一段重复的？
    @PostMapping("/updateAccountHour")
    public ResponseEntity<Map<String, Object>> updateAccountHour(@RequestBody Map<String, String> user) {
        String studentNumber = user.get("studentNumber");
        Long id = Long.parseLong(user.get("id"));
        var result = groupClassService.getById(id);
        boolean b = false;
        if (result != null) {
            b = groupClassService.saveGroupClassById(studentNumber, result);
        }
        Map<String, Object> response = new HashMap<>();
        if (b) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response); //返回JSON响应
    }

    //计算这个学生共报名了哪些课？
    //每次调用会再更新一遍他的classHour？
    //这个是不是可以删除？
    @PostMapping("/groupClass")
    public void postGroupClass(@RequestBody GroupClass groupClass) {
        groupClassService.saveGroupClass(groupClass);
    }

    //退课
    @PostMapping("/dropClass")
    public ResponseEntity<Map<String, Object>> dropClass(@RequestBody Map<String, String> user) {
        String studentNumber = user.get("studentNumber");
        Long id = Long.parseLong(user.get("id"));
        GroupClass result = groupClassService.getById(id);
        boolean b = false;
        if (result != null) {
            b = groupClassService.dropClassById(studentNumber, result);
        }
        Map<String, Object> response = new HashMap<>();
        if (b) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response); //返回给前端
    }


}



