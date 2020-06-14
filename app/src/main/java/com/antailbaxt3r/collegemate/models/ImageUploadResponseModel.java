package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUploadResponseModel {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("msg")
    @Expose
    private String message;

    public boolean getSuccess(){
        return this.success;
    }
    public String getMessage() {
        return this.message;
    }
}
