package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentPostResponseModel {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("assignment")
    @Expose
    private Assignment assignment;

    public Boolean getSuccess(){
        return this.success;
    }
    public void setSuccess(Boolean success){
        this.success = success;
    }

    public Assignment getAssignment(){
        return this.assignment;
    }
    public void setAssignment(Assignment assignment){
        this.assignment = assignment;
    }
}
