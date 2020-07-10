package com.antailbaxt3r.collegemate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryGetResponseModel {

    @SerializedName("success")
    @Expose
    boolean success;
    @SerializedName("files")
    @Expose
    List<Library> files;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Library> getFiles() {
        return files;
    }

    public void setFiles(List<Library> files) {
        this.files = files;
    }
}
