package com.antailbaxt3r.collegemate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antailbaxt3r.collegemate.databinding.FragmentRemindersBinding;

public class RemindersFragment extends Fragment {
    FragmentRemindersBinding remindersBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        remindersBinding =FragmentRemindersBinding.inflate(inflater,container,false);
        View root = remindersBinding.getRoot();

        return root;
    }
}
