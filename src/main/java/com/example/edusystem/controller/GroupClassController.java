package com.example.edusystem.controller;

import com.example.edusystem.dto.GroupClass;
import com.example.edusystem.service.GroupClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GroupClassController {

    @Autowired
    GroupClassService groupClassService;


    //想通过memberId，查到对应的class
    @GetMapping("/class/recent/{studentNumber}")
    public List<GroupClass> getClassByStudentNumber(@PathVariable("studentNumber") String studentNumber) {
        return groupClassService.getRecentClassByStudentNumber(studentNumber);
    }
}
