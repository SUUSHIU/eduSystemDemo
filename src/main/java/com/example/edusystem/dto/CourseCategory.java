package com.example.edusystem.dto;

public enum CourseCategory {
    /**
     * 精品课程
     * 53hour、68hour、82hour、96hour、126hour、
     */
    classCourse_1("精品课程53hour"),
    classCourse_2(""),
    classCourse_3(""),
    classCourse_4(""),
    classCourse_5(""),

    /**
     * Vip课程
     * 30hour、45hour、60hour、96hour、120hour、
     */
    vipCourse_1(""),
    vipCourse_2(""),
    vipCourse_3(""),
    vipCourse_4(""),
    vipCourse_5("");

    String value;

    CourseCategory(String value){
     this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CourseCategory getByValue(String value){
        for (CourseCategory courseCategory : CourseCategory.values()) {
            if (courseCategory.value.equals(value)){
                return courseCategory;
            }
        }
        return null;
    }
}
