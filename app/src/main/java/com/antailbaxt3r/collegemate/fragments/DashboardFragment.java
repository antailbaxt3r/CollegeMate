package com.antailbaxt3r.collegemate.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antailbaxt3r.collegemate.activities.AddAssignmentActivity;
import com.antailbaxt3r.collegemate.activities.AssignmentActivity;
import com.antailbaxt3r.collegemate.activities.SubjectsActivity;
import com.antailbaxt3r.collegemate.adapters.AssignmentRecyclerAdapter;
import com.antailbaxt3r.collegemate.adapters.SubjectRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.FragmentDashboardBinding;
import com.antailbaxt3r.collegemate.models.Assignment;
import com.antailbaxt3r.collegemate.models.AssignmentResponseModel;
import com.antailbaxt3r.collegemate.models.Subject;
import com.antailbaxt3r.collegemate.models.SubjectGetResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    FragmentDashboardBinding dashboardBinding;
    Context context;
    SharedPrefs prefs;
    //Subject Recycler View Adapter
    SubjectRecyclerAdapter subjectRecyclerAdapter;

    //Assignment Recycler View Adapter
    AssignmentRecyclerAdapter assignmentRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardBinding = FragmentDashboardBinding.inflate(inflater,container,false);
        View root = dashboardBinding.getRoot();

        //Context
        context = getActivity();

        //Initialising prefs
        prefs = new SharedPrefs(context);

        //Loading Subject Data
        loadSubjectData();

        //Loading Assignment Data
        loadAssignmentData();



        return root;
    }

    void setUpSubjectViewMore(){
        dashboardBinding.subjectMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SubjectsActivity.class);
                startActivity(i);
            }
        });
    }

    void setUpAssignmentViewMore(){
        dashboardBinding.assignmentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AssignmentActivity.class);
                startActivity(i);
            }
        });
    }

    void loadSubjectData(){
        Call<SubjectGetResponseModel> call = RetrofitClient.getClient().getSubject(prefs.getToken());
        call.enqueue(new Callback<SubjectGetResponseModel>() {
            @Override
            public void onResponse(Call<SubjectGetResponseModel> call, Response<SubjectGetResponseModel> response) {
                if(response.isSuccessful()){
                    //Saving downloaded Data
                    GeneralData.setSubjects(response.body().getSubjects());

                    //Setting up Recycler View
                    Log.i("DATA",response.body().getSubjects().toString());
                    setSubjectRecyclerView(response.body().getSubjects());
                }
            }

            @Override
            public void onFailure(Call<SubjectGetResponseModel> call, Throwable t) {
                Log.e("CALL Error",t.getMessage());
            }
        });


    }

    void loadAssignmentData(){
        Call<AssignmentResponseModel> call = RetrofitClient.getClient().getAssignments(prefs.getToken());
        call.enqueue(new Callback<AssignmentResponseModel>() {
            @Override
            public void onResponse(Call<AssignmentResponseModel> call, Response<AssignmentResponseModel> response) {
                if(response.isSuccessful()){
                    //Saving Downloaded Data
                    GeneralData.setAssignments(response.body().getAssignments());

                    //Setting up the Recycler View
                    setAssignmentRecyclerView(response.body().getAssignments());
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponseModel> call, Throwable t) {
                Log.e("CALL Error",t.getMessage());
            }
        });

    }

    void setSubjectRecyclerView(List<Subject> subjectData){
        if(subjectData.size() !=0){
            dashboardBinding.subjectMore.setText("View More");
            setUpSubjectViewMore();
        }

        //Setting maximum subject size = 5
        List<Subject> croppedList  = new ArrayList<>();
        for(int i =0; i<5 && i<subjectData.size();i++){
            croppedList.add(subjectData.get(i));
        }

        dashboardBinding.subjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
        subjectRecyclerAdapter = new SubjectRecyclerAdapter(croppedList,context);
        dashboardBinding.subjectRecyclerView.setAdapter(subjectRecyclerAdapter);
    }

    void setAssignmentRecyclerView(List<Assignment> assignmentData){
        if(assignmentData.size() !=0){
            dashboardBinding.assignmentMore.setText("View More");
            setUpAssignmentViewMore();
        }

        //Setting maximum assignment size = 5
        List<Assignment> croppedList = new ArrayList<>();
        for(int i =0; i<5 && i<assignmentData.size(); i++){
            croppedList.add(assignmentData.get(i));
        }

        dashboardBinding.assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,false));
        assignmentRecyclerAdapter = new AssignmentRecyclerAdapter(croppedList,context);
        dashboardBinding.assignmentRecyclerView.setAdapter(assignmentRecyclerAdapter);

    }
}
