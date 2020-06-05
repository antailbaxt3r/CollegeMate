package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("subject_title")
    @Expose
    private String subjectTitle;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getSubjectId(){
        return this.subjectId;
    }
    public void setSubjectId(Integer subjectId){
        this.subjectId = subjectId;
    }

    public String getSubjectTitle(){
        return this.subjectTitle;
    }
    public void setSubjectTitle(String subjectTitle){
        this.subjectTitle = subjectTitle;
    }

    public String getCourseCode(){
        return this.courseCode;
    }
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String getCreatedAt(){
        return this.getCreatedAt();
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getUpdatedAt(){
        return this.updatedAt;
    }
    public void setUpdatedAt(String updatedAt){
        this.createdAt = updatedAt;
    }



}
