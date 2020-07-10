package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.adapters.LibraryRecyclerAdapter;
import com.antailbaxt3r.collegemate.apiControllers.LibraryCtrl;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivityLibraryBinding;
import com.antailbaxt3r.collegemate.models.LibraryGetResponseModel;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;
    LibraryRecyclerAdapter adapter;
    SharedPrefs prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = new SharedPrefs(this);

        //

        loadData();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LibraryActivity.this,AddLibraryActivity.class);
                startActivity(i);
            }
        });
    }

    void loadData(){
        LibraryCtrl.getLibrary(prefs.getToken(), new Callback<LibraryGetResponseModel>() {
            @Override
            public void onResponse(Call<LibraryGetResponseModel> call, Response<LibraryGetResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.libraries = response.body().getFiles();
                    initializeRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<LibraryGetResponseModel> call, Throwable t) {

            }
        });
    }

    void initializeRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LibraryRecyclerAdapter(GeneralData.libraries,this);
        binding.recyclerView.setAdapter(adapter);
    }

    void updateRecyclerView(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateRecyclerView();
    }
}