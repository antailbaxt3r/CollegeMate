package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.adapters.SubjectRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivitySubjectsBinding;

public class SubjectsActivity extends AppCompatActivity {

    ActivitySubjectsBinding binding;
    SubjectRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Setting toolbar as action bar
        setSupportActionBar(binding.toolbar);

        setUpRecyclerView();
    }

    void setUpRecyclerView(){
        adapter = new SubjectRecyclerAdapter(GeneralData.getSubjects(),this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subjects_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}