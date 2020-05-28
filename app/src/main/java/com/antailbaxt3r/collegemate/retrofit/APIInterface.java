package com.antailbaxt3r.collegemate.retrofit;

import com.antailbaxt3r.collegemate.ModalClasses.ProfileModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("api/user/update")
    Call<ProfileModal> createProfile(@Body ProfileModal profileModal);

    @POST("api/google/signin")
    @FormUrlEncoded
    Call<POST> sendRegToken(@Field("id_token") String idtoken);

    
}
