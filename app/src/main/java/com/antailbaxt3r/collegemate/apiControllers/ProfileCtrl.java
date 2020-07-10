package com.antailbaxt3r.collegemate.apiControllers;

import com.antailbaxt3r.collegemate.models.User;
import com.antailbaxt3r.collegemate.models.UserUpdateResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileCtrl {

    public static void updateUser(User user, String token, Callback<UserUpdateResponseModel> callback){
        Map<String, String> map = new HashMap<>();
        map.put("enrollment_id", user.getEnrollmentId().toString().trim());
        map.put("phone", user.getPhone().toString().trim());
        map.put("year_of_study", user.getYearOfStudy().toString().trim());
        map.put("new_user", String.valueOf(false));
        Call<UserUpdateResponseModel> call = RetrofitClient.getClient().updateUser(token,map);
        call.enqueue(callback);
    }
}
