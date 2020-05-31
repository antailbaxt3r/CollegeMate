package com.antailbaxt3r.collegemate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.antailbaxt3r.collegemate.databinding.FragmentTimeTableBinding;
import com.antailbaxt3r.collegemate.R;

public class TimeTableFragment extends Fragment {

    FragmentTimeTableBinding timeTableBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_time_table, container, false);
        return root;
    }
}
