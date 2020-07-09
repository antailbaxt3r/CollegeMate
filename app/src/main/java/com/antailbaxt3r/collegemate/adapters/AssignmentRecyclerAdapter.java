package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.RecyclerAssignmentBinding;
import com.antailbaxt3r.collegemate.models.Assignment;
import com.antailbaxt3r.collegemate.models.AssignmentDeleteResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.tasks.AsyncTaskLoadAssignmentImage;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentRecyclerAdapter extends RecyclerView.Adapter<AssignmentRecyclerAdapter.ViewHolder> {
    private RecyclerAssignmentBinding binding;
    private List<Assignment> data;
    private Context context;
    private SharedPrefs prefs;

    public AssignmentRecyclerAdapter(List<Assignment> data, Context context) {
        this.data = data;
        this.context = context;

        prefs = new SharedPrefs(context);
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAssignment(data.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout imageLayout;
        ImageView imageView,delete;
        TextView title,description,subjectId,subjectName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLayout = binding.imageLayout;
            imageView = binding.image;
            title = binding.title;
            description = binding.description;
            subjectId = binding.subjectId;
            subjectName = binding.subjectName;
            delete = binding.delete;
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

    void deleteAssignment(Assignment assignment , int position){
        Map<String,Integer> body = new HashMap<>();
        body.put("assignment_id",assignment.getAssignmentId());
        Call<AssignmentDeleteResponseModel> call = RetrofitClient.getClient().deleteAssignment(prefs.getToken(),body);
        call.enqueue(new Callback<AssignmentDeleteResponseModel>() {
            @Override
            public void onResponse(Call<AssignmentDeleteResponseModel> call, Response<AssignmentDeleteResponseModel> response) {
                if (response.isSuccessful()){
                    //Local changes
                    GeneralData.assignments.remove(assignment);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<AssignmentDeleteResponseModel> call, Throwable t) {

            }
        });
    }
}
