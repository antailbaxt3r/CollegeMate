package com.antailbaxt3r.collegemate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    FragmentDashboardBinding dashboardBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }
}
