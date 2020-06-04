package com.antailbaxt3r.collegemate.data;

import com.antailbaxt3r.collegemate.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class GeneralData {
    //Objects which are frequently used
    public static List<Subject> subjects = new ArrayList<>();


    public static List<Subject> getSubjects(){
        return subjects;
    }
    public static void setSubjects(List<Subject> newSubjects){
        subjects= newSubjects;
    }

}
