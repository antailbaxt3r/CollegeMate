package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.ActivityLibraryBinding;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}