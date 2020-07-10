package com.antailbaxt3r.collegemate.data;

import com.antailbaxt3r.collegemate.models.Assignment;
import com.antailbaxt3r.collegemate.models.Classes;
import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class GeneralData {
    //Objects which are frequently used
    public static List<Subject> subjects = new ArrayList<>();
    public static List<Assignment> assignments = new ArrayList<>();
    public static List<Classes> classes = new ArrayList<>();
    public static List<Library> libraries = new ArrayList<>();
    
    public static List<Subject> getSubjects(){
        return subjects;
    }
    public static void setSubjects(List<Subject> newSubjects){
        subjects= newSubjects;
    }

    public static List<Assignment> getAssignments(){
        return assignments;
    }
    public static void setAssignments(List<Assignment> newAssignments){
        assignments = newAssignments;
    }
    public static List<Classes> getClasses(){
        return classes;
    }
    public static void setClasses(List<Classes> newClasses){
        classes = newClasses;
    }

}
