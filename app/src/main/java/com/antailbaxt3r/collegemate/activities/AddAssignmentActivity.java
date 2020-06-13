package com.antailbaxt3r.collegemate.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivityAddAssignmentBinding;
import com.antailbaxt3r.collegemate.models.AssignmentPostResponseModel;
import com.antailbaxt3r.collegemate.models.AssignmentResponseModel;
import com.antailbaxt3r.collegemate.models.ImageUploadResponseModel;
import com.antailbaxt3r.collegemate.models.Subject;
import com.antailbaxt3r.collegemate.retrofit.LocalRetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AddAssignmentActivity extends AppCompatActivity {
    ActivityAddAssignmentBinding binding;
    //Request Codes
    int RESULT_LOAD_IMAGE = 100;

    SharedPrefs pref;

    String subject,imagePath;
    Boolean imageLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        pref = new SharedPrefs(this);

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Setting image loading status as true
                imageLoading = true;
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });

        binding.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateSubjectPopUp();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }


    boolean isFilled(){
        if(binding.title.getText().toString().length() ==0){
            return false;
        }else if(binding.description.getText().toString().length() ==0){
            return false;
        }else if(subject == null){
            return false;
        }else if(imageLoading){
            return false;
        }

        return true;
    }

    void submitData(){
        if(!isFilled()){
            return;
        }
        Map<String,String>data = new HashMap<>();
        data.put("assignment_title",binding.title.getText().toString());
        data.put("assignment_description",binding.description.getText().toString());
        data.put("course_name",subject);
        if(imagePath!=null){
            data.put("image_path",imagePath);
        }

        Call<AssignmentPostResponseModel> call = LocalRetrofitClient.getClient().addAssignment(pref.getToken(),data);
        call.enqueue(new Callback<AssignmentPostResponseModel>() {
            @Override
            public void onResponse(Call<AssignmentPostResponseModel> call, Response<AssignmentPostResponseModel> response) {
                if(response.isSuccessful()){
                    Log.i("Upload Successful",response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<AssignmentPostResponseModel> call, Throwable t) {
                Log.e("Upload Unsuccessful",t.getMessage());
            }
        });


    }

    void inflateSubjectPopUp(){
        PopupMenu menu = new PopupMenu(this,binding.dropDownButton);

        //Populating popup menu
        List<Integer> ids = new Vector<>();
        int newId =0;
        for(Subject subject: GeneralData.getSubjects()){
            menu.getMenu().add(100,newId,newId,subject.getCourseCode()+": "+subject.getSubjectTitle());
            newId++;
        }

        //Adding Listeners
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                subject = GeneralData.getSubjects().get(menuItem.getItemId()).getSubjectTitle();
                binding.subject.setText(subject);
                return false;
            }
        });

        menu.show();
    }

    void onImageUploadSuccessful(Uri imageUri){
        imageLoading = false;
        try {
            Bitmap bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            binding.image.setImageBitmap(bitmap);

            binding.image.setVisibility(View.VISIBLE);

            //Hiding Add Image View
            binding.addImage.setVisibility(View.GONE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void uploadImage(byte[] imageBytes, final Uri uri){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
        Call<ImageUploadResponseModel> call = LocalRetrofitClient.getClient().uploadImage(pref.getToken(),body);
        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, Response<ImageUploadResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddAssignmentActivity.this, response.body().getPath(), Toast.LENGTH_SHORT).show();
                    Log.i("Upload Successful",response.body().getPath());

                    //Setting Image Path
                    imagePath = response.body().getPath();
                    onImageUploadSuccessful(uri);
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
                Log.e("Upload Unsuccessful",t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RESULT_LOAD_IMAGE){
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                uploadImage(getBytes(is),data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}