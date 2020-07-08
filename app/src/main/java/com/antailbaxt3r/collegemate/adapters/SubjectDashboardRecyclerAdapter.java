package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardItemsBinding;
import com.antailbaxt3r.collegemate.models.Subject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectDashboardRecyclerAdapter extends RecyclerView.Adapter<SubjectDashboardRecyclerAdapter.ViewHolder>{
    RecyclerDashboardItemsBinding binding;
    private List<Subject> data;
    private Context context;

    public SubjectDashboardRecyclerAdapter( List<Subject> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerDashboardItemsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subjectCode.setText(data.get(position).getCourseCode());
        holder.subjectName.setText(data.get(position).getSubjectTitle());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView subjectName;
        public TextView subjectCode;
        public ViewHolder(@NonNull View v){
            super(v);
            subjectName = binding.itemTitle;
            subjectCode = binding.itemInfo;
        }
    }
}
