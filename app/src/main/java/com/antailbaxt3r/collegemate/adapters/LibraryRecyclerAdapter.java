package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardItemsBinding;
import com.antailbaxt3r.collegemate.databinding.RecyclerLibraryBinding;
import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.tasks.AsyncTaskDownloadPdf;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {
    RecyclerLibraryBinding binding;
    List<Library> data;
    Context context;
    SharedPrefs prefs;

    public LibraryRecyclerAdapter(List<Library> data, Context context) {
        this.data = data;
        this.context = context;

        prefs = new SharedPrefs(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerLibraryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setTexts(holder,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            description = binding.description;

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    AsyncTaskDownloadPdf task = new AsyncTaskDownloadPdf(data.get(getAdapterPosition()).getPath(),context);
                    task.execute();
                }
            });
        }
    }

    void setTexts(ViewHolder holder,int position){
        Library library = data.get(position);
        holder.name.setText(library.getName());
        holder.description.setText(library.getDescription());
    }

}
