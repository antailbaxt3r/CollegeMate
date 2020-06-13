package com.antailbaxt3r.collegemate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.ActivityAssignmentBinding;

public class AssignmentActivity extends AppCompatActivity {
    ActivityAssignmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
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
}