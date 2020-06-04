package com.antailbaxt3r.collegemate.fragments;

import android.content.Context;
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
import android.widget.LinearLayout;

import com.antailbaxt3r.collegemate.adapters.DashboardSubjectRecyclerAdapter;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.FragmentDashboardBinding;
import com.antailbaxt3r.collegemate.models.Subject;
import com.antailbaxt3r.collegemate.models.SubjectResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    FragmentDashboardBinding dashboardBinding;
    Context context;
    SharedPrefs prefs;
    //Subject Recycler View Adapter
    DashboardSubjectRecyclerAdapter subjectRecyclerAdapter;
    //Assignment Recycler View Adapter


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

        return root;
    }

    void loadSubjectData(){
        Call<SubjectResponseModel> call = RetrofitClient.getClient().getSubject(prefs.getToken());
        call.enqueue(new Callback<SubjectResponseModel>() {
            @Override
            public void onResponse(Call<SubjectResponseModel> call, Response<SubjectResponseModel> response) {
                if(response.isSuccessful()){
                    //Saving downloaded Data
                    GeneralData.setSubjects(response.body().getSubjects());

                    //Setting up Recycler View
                    Log.i("DATA",response.body().getSubjects().toString());
                    setSubjectRecyclerView(response.body().getSubjects());
                }
            }

            @Override
            public void onFailure(Call<SubjectResponseModel> call, Throwable t) {
                Log.e("CALL Error",t.getMessage());
            }
        });


    }

    void setSubjectRecyclerView(List<Subject> subjectData){
        //Setting maximum subject size = 5
        List<Subject> croppedList  = new ArrayList<>();
        for(int i =0; i<5 && i<subjectData.size();i++){
            croppedList.add(subjectData.get(i));
        }

        dashboardBinding.subjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
        subjectRecyclerAdapter = new DashboardSubjectRecyclerAdapter(croppedList,context);
        dashboardBinding.subjectRecyclerView.setAdapter(subjectRecyclerAdapter);
    }
}
