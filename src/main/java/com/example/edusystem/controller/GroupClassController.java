package com.example.edusystem.controller;

import com.example.edusystem.service.GroupClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
public class GroupClassController {

    @Autowired
    GroupClassService groupClassService;


    @GetMapping("/class/{id}")
    public String getClassByAccountId() {
        //get id
        return "";
    }
}
