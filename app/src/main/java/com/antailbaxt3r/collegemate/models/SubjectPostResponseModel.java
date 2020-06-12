package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectPostResponseModel {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("subject")
    @Expose
    private Subject subject;

    public boolean getSuccess(){
        return this.success;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }

    public Subject getSubject(){
        return this.subject;
    }
    public void setSubject(Subject subject){
        this.subject = subject;
    }

}
