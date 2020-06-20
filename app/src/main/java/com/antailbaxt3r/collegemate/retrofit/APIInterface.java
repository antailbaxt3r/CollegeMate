package com.antailbaxt3r.collegemate.retrofit;

import com.antailbaxt3r.collegemate.models.AssignmentPostResponseModel;
import com.antailbaxt3r.collegemate.models.AssignmentResponseModel;
import com.antailbaxt3r.collegemate.models.ClassesResponseModel;
import com.antailbaxt3r.collegemate.models.ImageUploadResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectGetResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectPostResponseModel;
import com.antailbaxt3r.collegemate.models.TokenResponseModel;
import com.antailbaxt3r.collegemate.models.UserResponseModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("assignments/add")
    Call<AssignmentPostResponseModel> addAssignment(@Header("token") String token,@Body Map<String,String> map);

    @POST("timetable/add")
    Call<ClassesResponseModel> addClasses(@Header("token") String token, @Body Map<String,String> map);

    @Multipart
    @POST("assignments/upload")
    Call<ImageUploadResponseModel> uploadImage(@Header("token") String token, @Part MultipartBody.Part body, @Part("assignment_id") Integer id);



}
