package com.example.mrz.newproject.model.bean;

import java.io.Serializable;

/**
 * Created by Mr.z on 2017/9/19.
 *
 * 分数类
 */

public class ScoreBean implements Serializable {

    //学年
    private String scoreYear;

    //学期
    private String scoreTerm;

    //课程代码
    private String scoreId;

    //课程名
    private String scoreName;

    //课程性质
    private String scoreNature;

    //学分
    private String scoreCredit;

    //绩点
    private String scoreGarde;

    //成绩
    private String scoreResult;

    //学院
    private String scoreSchool;

    public String getScoreYear() {
        return scoreYear;
    }

    public void setScoreYear(String scoreYear) {
        this.scoreYear = scoreYear;
    }

    public String getScoreTerm() {
        return scoreTerm;
    }

    public void setScoreTerm(String scoreTerm) {
        this.scoreTerm = scoreTerm;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScoreNature() {
        return scoreNature;
    }

    public void setScoreNature(String scoreNature) {
        this.scoreNature = scoreNature;
    }

    public String getScoreCredit() {
        return scoreCredit;
    }

    public void setScoreCredit(String scoreCredit) {
        this.scoreCredit = scoreCredit;
    }

    public String getScoreGarde() {
        return scoreGarde;
    }

    public void setScoreGarde(String scoreGarde) {
        this.scoreGarde = scoreGarde;
    }

    public String getScoreResult() {
        return scoreResult;
    }

    public void setScoreResult(String scoreResult) {
        this.scoreResult = scoreResult;
    }

    public String getScoreSchool() {
        return scoreSchool;
    }

    public void setScoreSchool(String scoreSchool) {
        this.scoreSchool = scoreSchool;
    }
}
