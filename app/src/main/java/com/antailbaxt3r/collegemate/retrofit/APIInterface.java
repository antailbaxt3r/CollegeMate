package com.antailbaxt3r.collegemate.retrofit;

import com.antailbaxt3r.collegemate.models.AssignmentResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectGetResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectPostResponseModel;
import com.antailbaxt3r.collegemate.models.TokenResponseModel;
import com.antailbaxt3r.collegemate.models.UserResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("google/signin")
    @Headers({
        "Accept: application/json",
        "Content-Type: application/x-www-form-urlencoded"
    })
    Call<TokenResponseModel> getToken(@Field("idToken") String token);

    @POST("user/update")
    Call<UserResponseModel> updateUser(@Header("token") String token, @Body Map<String, String> map);

    @GET("subjects/get")
    Call<SubjectGetResponseModel> getSubject(@Header("token") String token);

    @POST("subjects/add")
    Call<SubjectPostResponseModel> addSubject(@Header("token") String token, @Body Map<String,String> map);

    @GET("assignments/get")
    Call<AssignmentResponseModel> getAssignments(@Header("token") String token);



}
