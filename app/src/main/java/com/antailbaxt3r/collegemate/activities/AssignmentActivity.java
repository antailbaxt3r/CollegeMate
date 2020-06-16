package com.antailbaxt3r.collegemate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.adapters.AssignmentRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivityAssignmentBinding;

public class AssignmentActivity extends AppCompatActivity {
    ActivityAssignmentBinding binding;
    AssignmentRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        //setting up recycler view
        setUpRecyclerView();
    }

    void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentRecyclerAdapter(GeneralData.getAssignments(),this);
        binding.recyclerView.setAdapter(adapter);
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void openAddAssignment(){
        Intent i = new Intent(this,AddAssignmentActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            openAddAssignment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assignment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostResume() {
        setUpRecyclerView();
        super.onPostResume();
    }
}