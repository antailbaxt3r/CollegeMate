package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardItemsBinding;
import com.antailbaxt3r.collegemate.models.Assignment;
import com.antailbaxt3r.collegemate.utils.DateFormatter;

import java.text.ParseException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentDashboardRecyclerAdapter extends RecyclerView.Adapter<AssignmentDashboardRecyclerAdapter.ViewHolder> {

    private RecyclerDashboardItemsBinding binding;
    private Context context;
    private List<Assignment> data;

    public AssignmentDashboardRecyclerAdapter(List<Assignment> data, Context context){
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subjectName.setText(data.get(position).getCourseName());
        try {
            holder.dueDate.setText(new DateFormatter(data.get(position).getDateDue()).getDateFormat1());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("PARSE ERROR",e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView subjectName;
        public TextView dueDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = binding.itemTitle;
            dueDate = binding.itemInfo;
        }
    }
}
