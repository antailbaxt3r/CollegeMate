package com.antailbaxt3r.collegemate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.adapters.SubjectRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivitySubjectsBinding;
import com.antailbaxt3r.collegemate.fragments.AddSubjectCallbacks;
import com.antailbaxt3r.collegemate.fragments.AddSubjectDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SubjectsActivity extends AppCompatActivity implements AddSubjectCallbacks {

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

    public void updateRecyclerView(){
        adapter.notifyDataSetChanged();
    }

    void setUpRecyclerView(){
        adapter = new SubjectRecyclerAdapter(GeneralData.getSubjects(),this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(adapter);
    }

    void showAddSubjectDialog(){
        //using BottomSheetDialogFragment
        AddSubjectDialogFragment fragment = new AddSubjectDialogFragment();
        fragment.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            showAddSubjectDialog();
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onSubjectAdded() {
        updateRecyclerView();
    }
}