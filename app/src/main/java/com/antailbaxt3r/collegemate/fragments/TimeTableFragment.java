package com.antailbaxt3r.collegemate.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antailbaxt3r.collegemate.activities.AddAssignmentActivity;
import com.antailbaxt3r.collegemate.activities.AddClassActivity;
import com.antailbaxt3r.collegemate.adapters.TimeTableRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.FragmentTimeTableBinding;

public class TimeTableFragment extends Fragment {
    FragmentTimeTableBinding binding;
    TimeTableRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimeTableBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddClassActivity.class);
                startActivity(i);
            }
        });

        setUpRecyclerView();
        return root;
    }

    void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TimeTableRecyclerAdapter(getActivity(),GeneralData.getClasses());
        binding.recyclerView.setAdapter(adapter);
    }

    void updateRecyclerView(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }
}
