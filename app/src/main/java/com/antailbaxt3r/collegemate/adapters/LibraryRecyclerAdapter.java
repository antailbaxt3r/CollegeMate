package com.antailbaxt3r.collegemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antailbaxt3r.collegemate.databinding.RecyclerDashboardItemsBinding;
import com.antailbaxt3r.collegemate.databinding.RecyclerLibraryBinding;
import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.util.List;

import androidx.annotation.NonNull;
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
        }
    }

    void setTexts(ViewHolder holder,int position){
        Library library = data.get(position);
        holder.name.setText(library.getName());
        holder.description.setText(library.getDescription());
    }
}
