package com.antailbaxt3r.collegemate.apiControllers;

import android.provider.MediaStore;

import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.models.LibraryDeleteResponseModel;
import com.antailbaxt3r.collegemate.models.LibraryGetResponseModel;
import com.antailbaxt3r.collegemate.models.LibraryPostResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LibraryCtrl {

    public static void postLibrary(Library library, String token, byte[] pdf, Callback<LibraryPostResponseModel> callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"),pdf);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file","library.pdf",requestBody);
        Call<LibraryPostResponseModel> call = RetrofitClient.getClient().addLibrary(token,part,library.getName(),library.getDescription());

        call.enqueue(callback);
    }

    public static void getLibrary(String token,Callback<LibraryGetResponseModel> callback){
        Call<LibraryGetResponseModel> call = RetrofitClient.getClient().getLibrary(token);
        call.enqueue(callback);
    }

    public static void deleteLibrary(String token, int libraryId,Callback<LibraryDeleteResponseModel> callback){
        Map<String,Integer> body = new HashMap<>();
        body.put("file_id",libraryId);
        Call<LibraryDeleteResponseModel> call = RetrofitClient.getClient().deleteLibrary(token,body);
    }
}
