package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardItemsBinding;
import com.antailbaxt3r.collegemate.databinding.RecyclerSubjectBinding;
import com.antailbaxt3r.collegemate.models.Subject;
import com.antailbaxt3r.collegemate.models.SubjectDeleteResponse;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {
    RecyclerSubjectBinding binding;

    private Context context;
    private List<Subject> data;

    private SharedPrefs prefs;
    public SubjectRecyclerAdapter(List<Subject> data, Context context){
        this.data = data;
        this.context = context;

        prefs  = new SharedPrefs(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerSubjectBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subjectCode.setText(data.get(position).getCourseCode());
        holder.subjectName.setText(data.get(position).getSubjectTitle());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSubject(data.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView subjectName;
        public TextView subjectCode;
        public ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = binding.itemTitle;
            subjectCode = binding.itemInfo;
            delete = binding.delete;
        }
    }

    void deleteSubject(Subject subject,int position){
        Map<String,Integer>body = new HashMap<>();
        body.put("subject_id",subject.getSubjectId());

        Call<SubjectDeleteResponse> call = RetrofitClient.getClient().deleteSubject(prefs.getToken(),body);
        call.enqueue(new Callback<SubjectDeleteResponse>() {
            @Override
            public void onResponse(Call<SubjectDeleteResponse> call, Response<SubjectDeleteResponse> response) {
                if(response.isSuccessful()){
                    //Removing Localdata
                    GeneralData.subjects.remove(subject);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<SubjectDeleteResponse> call, Throwable t) {

            }
        });
    }
}
