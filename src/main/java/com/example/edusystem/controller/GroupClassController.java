package com.example.edusystem.controller;

import com.example.edusystem.dto.GroupClass;
import com.example.edusystem.service.GroupClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GroupClassController {

    @Autowired
    GroupClassService groupClassService;


    //想通过memberId，查到对应的class
    @GetMapping("/class/recent/{studentNumber}")
    public List<GroupClass> getRecentClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getRecentClassByStudentNumber(studentNumber);
    }

    @GetMapping("/class/finish/{studentNumber}")
    public List<GroupClass> getFinishClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getFinishedClassByStudentNumber(studentNumber);
    }

    @GetMapping("/class/close/{studentNumber}")
    public List<GroupClass> getCloseClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getUpComingClassByStudentNumber(studentNumber);
    }


    @PostMapping("/groupClass")
    public void postGroupClass(@RequestBody GroupClass groupClass) {
        groupClassService.saveGroupClass(groupClass);
    }
}
