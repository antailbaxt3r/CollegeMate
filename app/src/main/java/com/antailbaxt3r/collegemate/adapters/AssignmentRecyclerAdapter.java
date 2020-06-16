package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.RecyclerAssignmentBinding;
import com.antailbaxt3r.collegemate.models.Assignment;
import com.antailbaxt3r.collegemate.tasks.AsyncTaskLoadAssignmentImage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentRecyclerAdapter extends RecyclerView.Adapter<AssignmentRecyclerAdapter.ViewHolder> {
    private RecyclerAssignmentBinding binding;
    private List<Assignment> data;
    private Context context;

    public AssignmentRecyclerAdapter(List<Assignment> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerAssignmentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Setting Text
        setText(holder,position);
        //Setting Image
        fetchImage(holder,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout imageLayout;
        ImageView imageView;
        TextView title,description,subjectId,subjectName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLayout = binding.imageLayout;
            imageView = binding.image;
            title = binding.title;
            description = binding.description;
            subjectId = binding.subjectId;
            subjectName = binding.subjectName;
        }
    }

    void setText(ViewHolder holder,int position){
        holder.title.setText(data.get(position).getAssignmentTitle());
        holder.subjectName.setText(data.get(position).getCourseName());
        holder.subjectId.setText(data.get(position).getCourseCode());

        holder.description.setText(data.get(position).getAssignmentDescription());

    }

    void fetchImage(ViewHolder holder,int position){
        if(data.get(position).getImagePath() == null){
            holder.imageLayout.setVisibility(View.GONE);
        }
        AsyncTaskLoadAssignmentImage task = new AsyncTaskLoadAssignmentImage(holder.imageView,context,data.get(position).getImagePath());

        task.execute();

    }
}
