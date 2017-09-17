package com.example.mrz.newproject.model.dao;

import com.example.mrz.newproject.model.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;


public class CourseDao {

    public static List<CourseBean>[] getCourseData() {

        List<CourseBean> courseModels[] = new ArrayList[7];

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }

        List<CourseBean> models_1 = new ArrayList<>();
        models_1.add(new CourseBean(0, "C语言", 1, 2, 1, "A401", (int) (Math.random() * 10)));
        models_1.add(new CourseBean(1, "Ruby", 3, 2, 1, "A453", (int) (Math.random() * 10)));
        models_1.add(new CourseBean(1, "PHP", 7, 2, 1, "A483", (int) (Math.random() * 10)));
        courseModels[0].addAll(models_1);

        List<CourseBean> models_2 = new ArrayList<>();
        models_2.add(new CourseBean(3, "Swift", 3, 2, 2, "A222", (int) (Math.random() * 10)));
        models_2.add(new CourseBean(3, "JavaScript", 5, 2, 2, "A777", (int) (Math.random() * 10)));
        courseModels[1].addAll(models_2);

        List<CourseBean> models_3 = new ArrayList<>();
        models_3.add(new CourseBean(3, "Python", 1, 2, 3, "A342", (int) (Math.random() * 10)));
        models_3.add(new CourseBean(3, "Visual Basic .NET", 3, 2, 3, "A737", (int) (Math.random() * 10)));
        courseModels[2].addAll(models_3);

        List<CourseBean> models_4 = new ArrayList<>();
        models_4.add(new CourseBean(4, "C#", 1, 2, 4, "A666", (int) (Math.random() * 10)));
        models_4.add(new CourseBean(5, "R语言", 7, 2,4, "A888", (int) (Math.random() * 10)));
        models_4.add(new CourseBean(5, "Java", 9, 2, 4, "A828", (int) (Math.random() * 10)));
        courseModels[3].addAll(models_4);

        List<CourseBean> models_5 = new ArrayList<>();
        models_5.add(new CourseBean(6, "Android", 1, 2, 5, "A466", (int) (Math.random() * 10)));
        models_5.add(new CourseBean(7, "Groovy", 3, 2, 5, "A434", (int) (Math.random() * 10)));
        models_5.add(new CourseBean(8, "Objective-C", 5, 2, 5, "A411", (int) (Math.random() * 10)));
        courseModels[4].addAll(models_5);

        List<CourseBean> models_6 = new ArrayList<>();
        models_6.add(new CourseBean(9, "C++", 1, 2, 6, "A422", (int) (Math.random() * 10)));
        models_6.add(new CourseBean(10, "SQL", 7, 2, 6, "A402", (int) (Math.random() * 10)));
        courseModels[5].addAll(models_6);

        return courseModels;

    }
}
