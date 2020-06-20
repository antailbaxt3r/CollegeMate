package com.antailbaxt3r.collegemate.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antailbaxt3r.collegemate.activities.AddAssignmentActivity;
import com.antailbaxt3r.collegemate.databinding.FragmentRemindersBinding;

public class RemindersFragment extends Fragment {
    FragmentRemindersBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentRemindersBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        return root;
    }
}
