package com.antailbaxt3r.collegemate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.ActivityRegisterBinding;
import com.antailbaxt3r.collegemate.models.UserResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        prefs = new SharedPrefs(this);
        binding.registerButton.setOnClickListener((v) -> {
            checkEnrollmentId();
            checkYear();
            checkPhone();
            if(
                binding.enrollmentId.getError() == null &&
                binding.yearOfStudy.getError() == null &&
                binding.phone.getError() == null
            ){
                register();
            }
        });
    }

    private void register() {
        Map<String, String> map = new HashMap<>();
        map.put("enrollment_id", binding.enrollmentId.getText().toString().trim());
        map.put("phone", binding.enrollmentId.getText().toString().trim());
        map.put("year_of_study", binding.yearOfStudy.getText().toString().trim());
        map.put("new_user", String.valueOf(false));
        Call<UserResponseModel> call = RetrofitClient.getClient().updateUser(prefs.getToken(), map);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                if(response.isSuccessful()){
                    UserResponseModel userResponse = response.body();
                    prefs.saveNewUser(false);
                    Toast.makeText(RegisterActivity.this, "User Created Successfully!", Toast.LENGTH_SHORT).show();
                    Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                Log.e("CALL Error", t.getMessage());
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEnrollmentId(){
        try {
            if (binding.enrollmentId.getText().toString().trim().isEmpty()){
                binding.enrollmentId.setError("Enrollment Id cannot be empty");
            }else{
                binding.enrollmentId.setError(null);
            }
        }catch (Exception e){
            binding.enrollmentId.setError("Enrollment Id cannot be empty");
        }
    }

    private void checkPhone(){
        try {
            String regex = "[0-9]{10}";
            String phoneNumber = binding.phone.getText().toString().trim();
            if (!phoneNumber.isEmpty() && phoneNumber.matches(regex)){
                binding.phone.setError(null);
            }else{
                binding.phone.setError("Invalid phone number");
            }
        }catch (Exception e){
            binding.phone.setError("Invalid phone number");
        }
    }

    private void checkYear(){
        try {
            String regex = "[0-9]{1}";
            String year = binding.yearOfStudy.getText().toString().trim();
            if (!year.isEmpty() && year.matches(regex)){
                binding.yearOfStudy.setError(null);
            }else{
                binding.yearOfStudy.setError("Invalid year");
            }
        }catch (Exception e){
            binding.yearOfStudy.setError("Invalid year");
        }
    }
}
