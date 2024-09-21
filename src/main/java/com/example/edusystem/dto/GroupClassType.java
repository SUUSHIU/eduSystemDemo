package com.example.edusystem.dto;

public enum GroupClassType {

    /**
     * 研究计划书
     * 设计类、纯艺类、ACG
     */
    researchProposalClass_1("researchProposalClass"),


    /**
     * 小论文班课
     * 设计类、纯艺类、ACG
     */
    EssayClass_1("小论文"),

    /**
     * 模拟面试
     * 排版课、制本课
     */
    interviewClass_1("模拟面试"),
    PortfolioLayoutClass_2("排版课"),
    bookDesignClass_3("制本课");


    String value;

    GroupClassType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    //前端传来“String”的值时，返回一个GroupClassType的对象
    public static GroupClassType getByValue(String value){
        for ( GroupClassType groupClassType : GroupClassType.values()) {
            if (groupClassType.value.equals(value)){
                return groupClassType;
            }
        }
        return null;
    }

}

