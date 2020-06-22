package com.antailbaxt3r.collegemate.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.activities.AddClassActivity;
import com.antailbaxt3r.collegemate.adapters.TimeTableRecyclerAdapter;
import com.antailbaxt3r.collegemate.comparators.TimetableComparator;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.FragmentTimeTableBinding;
import com.antailbaxt3r.collegemate.models.Classes;
import com.antailbaxt3r.collegemate.models.ClassesPostResponseModel;
import com.antailbaxt3r.collegemate.models.ClassesGetResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class TimeTableFragment extends Fragment {
    FragmentTimeTableBinding binding;
    TimeTableRecyclerAdapter adapter;
    SharedPrefs prefs;

    int currentDay;
    String days[];

    PopupMenu dayMenu;

    Context context;

    //List of classes of a particular day
    List<Classes> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setting context
        context = getActivity();
        binding = FragmentTimeTableBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        days = context.getResources().getStringArray(R.array.days);

        //Shared prefs
        prefs = new SharedPrefs(context);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddClassActivity.class);
                startActivity(i);
            }
        });

        //Set up day menu
        setUpDayPopMenu();

        //Set Today's day
        setDay();

        //Get Data
        loadData();

        binding.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayMenu.show();
            }
        });

        return root;
    }

    private void setDay(){
        Calendar calendar = new GregorianCalendar();
        currentDay = calendar.get(Calendar.DAY_OF_WEEK)-1;

        binding.day.setText(days[currentDay]);
    }

    private void makeData(){
        data = new ArrayList<>();
        for(Classes classes: GeneralData.classes){
            if(classes!=null && classes.getDay() == currentDay){
                data.add(classes);
            }
        }

        //Sorting data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data.sort(new TimetableComparator());
        }
    }

    private void loadData(){
        Call<ClassesGetResponseModel> call = RetrofitClient.getClient().getClasses(prefs.getToken());
        call.enqueue(new Callback<ClassesGetResponseModel>() {
            @Override
            public void onResponse(Call<ClassesGetResponseModel> call, Response<ClassesGetResponseModel> response) {
                if(response.isSuccessful()){
                    Log.i("Success","Successful");
                    //Saving data
                    GeneralData.setClasses(response.body().getClasses());
                    makeData();
                    setUpRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<ClassesGetResponseModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("Error",t.getMessage());
            }
        });
    }


    private void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TimeTableRecyclerAdapter(getActivity(),data);
        binding.recyclerView.setAdapter(adapter);
    }

    private void updateRecyclerView(){
        setDay();
        makeData();
        setUpRecyclerView();
    }

    private void setUpDayPopMenu(){
        dayMenu = new PopupMenu(context,binding.dayDropDown);

        for(int i =0 ;i<7 ; i++){
            dayMenu.getMenu().add(100,i,i,days[i]);
        }
        dayMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                currentDay = menuItem.getItemId();
                binding.day.setText(days[currentDay]);

                makeData();
                setUpRecyclerView();
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }
}
