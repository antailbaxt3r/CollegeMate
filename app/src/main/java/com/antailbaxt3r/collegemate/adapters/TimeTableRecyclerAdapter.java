package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.RecyclerTimetableBinding;
import com.antailbaxt3r.collegemate.models.Classes;
import com.antailbaxt3r.collegemate.utils.TimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableRecyclerAdapter extends RecyclerView.Adapter<TimeTableRecyclerAdapter.ViewHolder> {

    private RecyclerTimetableBinding binding;
    private Context context;
    private List<Classes> data = new ArrayList<>();

    public TimeTableRecyclerAdapter(Context context, List<Classes> data) {
        this.context = context;
        this.data = data;
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
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView start,end,venue,faculty,courseName,courseCode;

        public ViewHolder(View v){
            super(v);

            start = binding.start;
            end = binding.end;
            venue = binding.venue;
            faculty = binding.faculty;
            courseName = binding.courseName;
            courseCode = binding.courseCode;

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
}
