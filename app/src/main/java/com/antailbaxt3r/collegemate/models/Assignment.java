package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @SerializedName("login_id")
    @Expose
    private Integer loginId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("date_due")
    @Expose
    private String dateDue;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

    public String getAssignmentId(){
        return this.assignmentId;
    }
    public void setAssignmentId(String assignmentId){
        this.assignmentId = assignmentId;
    }

    public Integer getLoginId(){
        return this.loginId;
    }
    public void setLoginId(Integer loginId){
        this.loginId = loginId;
    }

    public String getCourseName(){
        return this.courseName;
    }
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getDateDue(){
        return this.dateDue;
    }
    public void setDateDue(String dateDue){
        this.dateDue = dateDue;
    }

    public String getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getUpdatedAt(){
        return this.updatedAt;
    }
    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt(){
        return this.deletedAt;
    }
    public void setDeletedAt(String deletedAt){
        this.deletedAt = deletedAt;
    }



}
