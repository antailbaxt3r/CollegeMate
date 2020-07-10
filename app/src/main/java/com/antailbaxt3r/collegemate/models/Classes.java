package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classes {
    @SerializedName("class_id")
    @Expose
    int classId;
    @SerializedName("course_name")
    @Expose
    String courseName;
    @SerializedName("course_code")
    @Expose
    String courseCode;
    @SerializedName("login_id")
    @Expose
    String loginId;
    @SerializedName("faculty")
    @Expose
    String faculty;
    @SerializedName("venue")
    @Expose
    String venue;
    @SerializedName("start")
    @Expose
    String start;
    @SerializedName("end")
    @Expose
    String end;
    @SerializedName("day")
    @Expose
    int day;

    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
