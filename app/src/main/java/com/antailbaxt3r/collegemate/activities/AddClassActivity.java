package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.antailbaxt3r.collegemate.databinding.ActivityAddClassBinding;

public class AddClassActivity extends AppCompatActivity {
    ActivityAddClassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}