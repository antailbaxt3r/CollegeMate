package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;
    @SerializedName("assignment_title")
    @Expose
    private String assignmentTitle;
    @SerializedName("assignment_description")
    @Expose
    private String assignmentDescription;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("login_id")
    @Expose
    private Integer loginId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
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

    public Integer getAssignmentId(){
        return this.assignmentId;
    }
    public void setAssignmentId(Integer assignmentId){
        this.assignmentId = assignmentId;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getLoginId(){
        return this.loginId;
    }
    public void setLoginId(Integer loginId){
        this.loginId = loginId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }
    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }
    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
