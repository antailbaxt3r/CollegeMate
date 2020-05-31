
package com.antailbaxt3r.collegemate.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("user")
    @Expose
    private User user = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
