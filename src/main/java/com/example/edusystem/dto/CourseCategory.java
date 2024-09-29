package com.example.edusystem.dto;

public enum CourseCategory {
    /**
     * 精品课程
     * 53hour、68hour、82hour、96hour、126hour、
     */
    classCourse_1("精品课程53hour"),
    classCourse_2("精品课程68hour"),
    classCourse_3("精品课程82hour"),
    classCourse_4("精品课程96hour"),
    classCourse_5("精品课程126hour"),

    /**
     * Vip课程
     * 30hour、45hour、60hour、96hour、120hour、
     */
    vipCourse_0("vip10"),
    vipCourse_1("vip30"),
    vipCourse_2("vip45"),
    vipCourse_3("vip60"),
    vipCourse_4("vip96"),
    vipCourse_5("vip126");

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
