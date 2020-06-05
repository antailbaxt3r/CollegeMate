package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.FragmentDashboardBinding;
import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardSubjectBinding;
import com.antailbaxt3r.collegemate.models.Subject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardSubjectRecyclerAdapter extends RecyclerView.Adapter<DashboardSubjectRecyclerAdapter.ViewHolder> {
    RecyclerDashboardSubjectBinding binding;

    private Context context;
    private List<Subject> data;

    public DashboardSubjectRecyclerAdapter(List<Subject> data, Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerDashboardSubjectBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subjectCode.setText(data.get(position).getCourseCode());
        holder.subjectName.setText(data.get(position).getSubjectTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView subjectName;
        public TextView subjectCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = binding.subjectName;
            subjectCode = binding.subjectCode;
        }
    }
}
