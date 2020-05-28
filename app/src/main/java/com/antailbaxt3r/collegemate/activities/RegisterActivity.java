package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.ModalClasses.ProfileModal;
import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.retrofit.APIInterface;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity{
    MaterialEditText usernameinput,enrollmentidinput,yearinput, phoneinput;
    FirebaseAuth auth;
    ProfileModal profile;
    LinearLayout submit;
    LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Referencing Views
        usernameinput = findViewById(R.id.username);
        enrollmentidinput = findViewById(R.id.enrollment_id);
        yearinput = findViewById(R.id.year_of_study);
        phoneinput = findViewById(R.id.phone);
        submit = findViewById(R.id.register_submit);
        parent = findViewById(R.id.register_layout);

        //Instancing Firebase Objects
        auth = FirebaseAuth.getInstance();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFilled()){
                     profile = new ProfileModal(
                            usernameinput.getText().toString(),
                            auth.getCurrentUser().getEmail(),
                            phoneinput.getText().toString(),
                            yearinput.getText().toString(),
                            enrollmentidinput.getText().toString()
                    );
                    uploadOnDatabase();
                }
            }
        });




    }

    void switchActivity(){
        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
        Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show();
    }



    void uploadOnDatabase(){
        APIInterface apiInterface = RetrofitClient.getClient();
        apiInterface.createProfile(profile).enqueue(new Callback<ProfileModal>() {
            @Override
            public void onResponse(Call<ProfileModal> call, Response<ProfileModal> response) {
                switchActivity();
            }

            @Override
            public void onFailure(Call<ProfileModal> call, Throwable t) {
                Snackbar.make(parent,t.getMessage(),Snackbar.LENGTH_SHORT).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadOnDatabase();
                    }
                }).show();
            }
        });
    }

    Boolean isFilled(){
        if(usernameinput.getText().toString().length() ==0){
            Snackbar.make(parent,"Enter Username",Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(phoneinput.getText().toString().length() ==0){
            Snackbar.make(parent,"Enter Phone Number",Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(yearinput.getText().toString().length() ==0){
            Snackbar.make(parent,"Enter Year",Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(enrollmentidinput.getText().toString().length() ==0){
            Snackbar.make(parent,"Enter Enrollment Number",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
