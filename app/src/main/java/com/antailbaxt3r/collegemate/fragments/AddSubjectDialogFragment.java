package com.antailbaxt3r.collegemate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.DialogAddSubjectBinding;
import com.antailbaxt3r.collegemate.models.SubjectPostResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubjectDialogFragment extends BottomSheetDialogFragment{
    DialogAddSubjectBinding binding;
    SharedPrefs prefs;
    private AddSubjectCallbacks callbacks;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callbacks = (AddSubjectCallbacks) context;
        }catch (ClassCastException e){
            Log.e("Class Cast Exeption",e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DialogAddSubjectBinding.inflate(inflater,container,false);
        prefs = new SharedPrefs(getContext());

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject();
            }
        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return binding.getRoot();
    }

    void addSubject(){
        if(!isFilled()){
            return;
        }
        Map<String,String> data = new HashMap<>();
        data.put("subject_title",binding.name.getText().toString());
        data.put("course_code",binding.courseCode.getText().toString());

        Call<SubjectPostResponseModel> call = RetrofitClient.getClient().addSubject(prefs.getToken(),data);
        call.enqueue(new Callback<SubjectPostResponseModel>() {
            @Override
            public void onResponse(Call<SubjectPostResponseModel> call, Response<SubjectPostResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Subject Added Successfully!", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();

                    //Adding Data Locally
                    GeneralData.subjects.add(response.body().getSubject());
                    //Triggering Activity Callback
                    callbacks.onSubjectAdded();
                }
            }
            @Override
            public void onFailure(Call<SubjectPostResponseModel> call, Throwable t) {

            }
        });
    }

    private boolean isFilled(){
        if(binding.name.getText().toString().length() ==0){
            Toast.makeText(getContext(), "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(binding.courseCode.getText().toString().length() ==0){
            Toast.makeText(getContext(), "Please Enter Course Code", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
