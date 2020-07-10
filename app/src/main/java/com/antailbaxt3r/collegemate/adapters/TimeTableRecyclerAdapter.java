package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.RecyclerTimetableBinding;
import com.antailbaxt3r.collegemate.models.Classes;
import com.antailbaxt3r.collegemate.models.ClassesDeleteResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.antailbaxt3r.collegemate.utils.TimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeTableRecyclerAdapter extends RecyclerView.Adapter<TimeTableRecyclerAdapter.ViewHolder> {

    private RecyclerTimetableBinding binding;
    private Context context;
    private List<Classes> data = new ArrayList<>();

    private SharedPrefs prefs;

    public TimeTableRecyclerAdapter(Context context, List<Classes> data) {
        this.context = context;
        this.data = data;

        prefs = new SharedPrefs(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerTimetableBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setUpTextViews(holder,position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClass(data.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView start,end,venue,faculty,courseName,courseCode;
        ImageView delete;

        public ViewHolder(View v){
            super(v);

            start = binding.start;
            end = binding.end;
            venue = binding.venue;
            faculty = binding.faculty;
            courseName = binding.courseName;
            courseCode = binding.courseCode;
            delete = binding.delete;
        }
    }

    void setUpTextViews(ViewHolder holder,int position){
        Classes classes = data.get(position);
        try{
            holder.start.setText(new TimeFormatter(classes.getStart()).getTimeFormat1());
            holder.end.setText(new TimeFormatter(classes.getEnd()).getTimeFormat1());
        }catch (ParseException e){
            e.printStackTrace();
        }

        holder.venue.setText(classes.getVenue());
        holder.courseCode.setText(classes.getCourseCode());
        holder.courseName.setText(classes.getCourseName());

        //Setting faculty
        if(classes.getFaculty() != null){
            holder.faculty.setText(classes.getFaculty());
        }
    }

    void deleteClass(Classes classes,int position){
        Map<String,Integer> body = new HashMap<>();
        body.put("class_id",classes.getClassId());
        Call<ClassesDeleteResponseModel> call = RetrofitClient.getClient().deleteClasses(prefs.getToken(),body);
        call.enqueue(new Callback<ClassesDeleteResponseModel>() {
            @Override
            public void onResponse(Call<ClassesDeleteResponseModel> call, Response<ClassesDeleteResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.classes.remove(classes);
                    data.remove(classes);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<ClassesDeleteResponseModel> call, Throwable t) {

            }
        });

    }
}
