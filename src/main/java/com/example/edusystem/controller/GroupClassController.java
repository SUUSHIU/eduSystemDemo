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


@RestController
@RequestMapping("/api")
public class GroupClassController {

    @Autowired
    GroupClassService groupClassService;


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

    //classType -> className
    @GetMapping("/class/chooseClass/{value}")
    public List<GroupClass> getGroupClassNameByGroupClassType(@PathVariable("value") String value) {
        GroupClassType courseCategory = GroupClassType.getByValue(value);
        return groupClassService.getGroupClassNameByGroupClassType(courseCategory);
    }

    //className -> baseClassName
    @GetMapping("/class/chooseClass/{name}")
    public List<GroupClass> getGroupClassNameByGroupClassTypeAndName(@PathVariable("name") String name) {
        return groupClassService.getBaseClassNameByGroupClassName(name);
    }

    //获得课程的id 和学号 studentNumber -> 把学号写进对象的studentNumberList中
    //同时座位 - 1
    @PostMapping("/chooseClass")
    public ResponseEntity<Map<String, Object>> chooseClass(@RequestBody Map<String, String> user) {
        String studentNumber = user.get("studentNumber"); //前端的值是String， 后端get的也是String
        Long id = Long.parseLong(user.get("id")); //
        var result = groupClassService.getById(id);
        boolean b = false;
        if (result != null){
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


    @PostMapping("/updateMemberTime")
    public ResponseEntity<Map<String, Object>> updateMemberTime(@RequestBody Map<String, String> user) {
        String studentNumber = user.get("studentNumber"); //前端的值是String， 后端get的也是String
        Long id = Long.parseLong(user.get("id")); //
        var result = groupClassService.getById(id);
        boolean b = false;
        if (result != null){
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


    @PostMapping("/groupClass")
    public void postGroupClass(@RequestBody GroupClass groupClass) {
        groupClassService.saveGroupClass(groupClass);
    }





}

