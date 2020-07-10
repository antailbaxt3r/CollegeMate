package com.antailbaxt3r.collegemate.retrofit;

import com.antailbaxt3r.collegemate.models.AssignmentDeleteResponseModel;
import com.antailbaxt3r.collegemate.models.AssignmentPostResponseModel;
import com.antailbaxt3r.collegemate.models.AssignmentResponseModel;
import com.antailbaxt3r.collegemate.models.ClassesGetResponseModel;
import com.antailbaxt3r.collegemate.models.ClassesPostResponseModel;
import com.antailbaxt3r.collegemate.models.ImageUploadResponseModel;
import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.models.LibraryDeleteResponseModel;
import com.antailbaxt3r.collegemate.models.LibraryGetResponseModel;
import com.antailbaxt3r.collegemate.models.LibraryPostResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectDeleteResponse;
import com.antailbaxt3r.collegemate.models.SubjectGetResponseModel;
import com.antailbaxt3r.collegemate.models.SubjectPostResponseModel;
import com.antailbaxt3r.collegemate.models.ClassesDeleteResponseModel;
import com.antailbaxt3r.collegemate.models.TokenResponseModel;
import com.antailbaxt3r.collegemate.models.UserGetResponseModel;
import com.antailbaxt3r.collegemate.models.UserUpdateResponseModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    Call<UserUpdateResponseModel> updateUser(@Header("token") String token, @Body Map<String, String> map);
    @GET("user/get")
    Call<UserGetResponseModel> getUser(@Header("token") String token);

    @GET("subjects/get")
    Call<SubjectGetResponseModel> getSubject(@Header("token") String token);
    @POST("subjects/add")
    Call<SubjectPostResponseModel> addSubject(@Header("token") String token, @Body Map<String,String> map);
    @HTTP(method = "DELETE", path = "subjects/delete", hasBody = true)
    Call<SubjectDeleteResponse> deleteSubject(@Header("token") String token, @Body Map<String,Integer> map);

    @GET("assignments/get")
    Call<AssignmentResponseModel> getAssignments(@Header("token") String token);
    @POST("assignments/add")
    Call<AssignmentPostResponseModel> addAssignment(@Header("token") String token,@Body Map<String,String> map);
    @HTTP(method = "DELETE", path = "assignments/delete", hasBody = true)
    Call<AssignmentDeleteResponseModel> deleteAssignment(@Header("token") String token, @Body Map<String,Integer> map);

    @POST("timetable/add")
    Call<ClassesPostResponseModel> addClasses(@Header("token") String token, @Body Map<String,String> map);
    @GET("timetable/get")
    Call<ClassesGetResponseModel> getClasses(@Header("token") String token);
    @HTTP(method = "DELETE", path = "timetable/delete", hasBody = true)
    Call<ClassesDeleteResponseModel> deleteClasses(@Header("token")String token, @Body Map<String,Integer>map);

    @Multipart
    @POST("assignments/upload")
    Call<ImageUploadResponseModel> uploadImage(@Header("token") String token, @Part MultipartBody.Part body, @Part("assignment_id") Integer id);

    @Multipart
    @POST("library/add")
    Call<LibraryPostResponseModel> addLibrary(@Header("token") String token, @Part MultipartBody.Part body, @Part("name") String name, @Part("description") String description);
    @GET("library/get")
    Call<LibraryGetResponseModel> getLibrary(@Header("token") String token);
    @HTTP(method = "DELETE", path = "timetable/delete", hasBody = true)
    Call<LibraryDeleteResponseModel> deleteLibrary(@Header("token") String token, @Body Map<String,Integer> body);



}
