package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUploadResponseModel {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("image_path")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
