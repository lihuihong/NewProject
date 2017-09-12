package com.example.mrz.newproject.model.bean;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class Class {

    private String className;
    private String classTeacter;
    private String classTime;
    private String classPlace;
    private String classScore;
    private String classTotal;
    private String classSelected;
    private String classMargin;

    private boolean flag;       //设置是否已选

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTeacter() {
        return classTeacter;
    }

    public void setClassTeacter(String classTeacter) {
        this.classTeacter = classTeacter;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public String getClassScore() {
        return classScore;
    }

    public void setClassScore(String classScore) {
        this.classScore = classScore;
    }

    public String getClassTotal() {
        return classTotal;
    }

    public void setClassTotal(String classTotal) {
        this.classTotal = classTotal;
    }

    public String getClassSelected() {
        return classSelected;
    }

    public void setClassSelected(String classSelected) {
        this.classSelected = classSelected;
    }

    public String getClassMargin() {
        return classMargin;
    }

    public void setClassMargin(String classMargin) {
        this.classMargin = classMargin;
    }
}
