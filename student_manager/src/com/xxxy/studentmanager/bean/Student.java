package com.xxxy.studentmanager.bean;

/**
 * 学生实体类
 */
public class Student {
    private Integer stuId;                //学生的id，唯一标识
    private String stuNo;                  //学生的学号
    private String stuName;                //学生的名字
    private String stuSex;                 //学生的性别
    private String stuCollage;             //学生的学院
    private String stuClass;               //学生的班级

    public Student(Integer stuId, String stuNo, String stuName, String stuSex, String stuCollage, String stuClass) {
        this.stuId = stuId;
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuCollage = stuCollage;
        this.stuClass = stuClass;
    }

    public Student() {
    }

    public Integer getUserId() {
        return stuId;
    }

    public void setUserId(Integer userId) {
        this.stuId = userId;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuCollage() {
        return stuCollage;
    }

    public void setStuCollage(String stuCollage) {
        this.stuCollage = stuCollage;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }
}
