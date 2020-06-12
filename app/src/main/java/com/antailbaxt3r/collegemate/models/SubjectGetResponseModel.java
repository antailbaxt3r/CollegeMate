package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectGetResponseModel {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects;

    public boolean getSuccess(){
        return this.success;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }

    public List<Subject> getSubjects(){
        return this.subjects;
    }
    public void setSubjects(List<Subject> subjects){
        this.subjects = subjects;
    }


}
