package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentResponseModel {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments;

    public Boolean getSuccess(){
        return this.success;
    }
    public void setSuccess(Boolean success){
        this.success = success;
    }

    public List<Assignment> getAssignments(){
        return this.assignments;
    }
    public void setAssignments(List<Assignment> assignments){
        this.assignments = assignments;
    }
}
