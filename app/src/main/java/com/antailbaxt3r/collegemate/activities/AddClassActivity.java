package com.antailbaxt3r.collegemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivityAddClassBinding;
import com.antailbaxt3r.collegemate.models.ClassesResponseModel;
import com.antailbaxt3r.collegemate.models.Subject;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.DateFormatter;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.antailbaxt3r.collegemate.utils.TimeFormatter;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AddClassActivity extends AppCompatActivity {
    ActivityAddClassBinding binding;
    SharedPrefs prefs;

    String courseCode, courseName,start,end,faculty,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setting toolbar
        setSupportActionBar(binding.toolbar);
        //Shared Preferences
        prefs = new SharedPrefs(this);

        binding.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDayPopUpMenu();
            }
        });

        binding.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSubjectPopUpMenu();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Read Edit Texts
                readEditText();
                onSubmit();
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpStartTimePicker();
            }
        });
        binding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpEndTimePicker();
            }
        });
    }

    void onSubmit(){
        if(!isFilled()){
            return;
        }

        Map<String,String> data = new HashMap<>();
        data.put("course_name",courseName);
        data.put("course_code",courseCode);
        data.put("start",start);
        data.put("end",end);
        data.put("day",day);
        if(faculty!=null){
            data.put("faculty",faculty);
        }

        Call<ClassesResponseModel> call = RetrofitClient.getClient().addClasses(prefs.getToken(),data);
        call.enqueue(new Callback<ClassesResponseModel>() {
            @Override
            public void onResponse(Call<ClassesResponseModel> call, Response<ClassesResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.classes.add(response.body().getClasses());
                    Toast.makeText(AddClassActivity.this, "Class Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ClassesResponseModel> call, Throwable t) {
                Toast.makeText(AddClassActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void readEditText(){
        faculty = binding.faculty.getText().toString();
    }

    boolean isFilled(){
        if(courseCode == null){
            Toast.makeText(this, "Please select a subject", Toast.LENGTH_SHORT).show();
            return false;
        }else if(start ==null){
            Toast.makeText(this, "Please select start time", Toast.LENGTH_SHORT).show();
            return false;
        }else if(end ==null){
            Toast.makeText(this, "Please select end time", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    void setSubjectPopUpMenu(){
        PopupMenu menu = new PopupMenu(this,binding.subjectDropDown);

        //Populating popup menu
        List<Integer> ids = new Vector<>();
        int newId =0;
        for(Subject subject: GeneralData.getSubjects()){
            menu.getMenu().add(100,newId,newId,subject.getCourseCode()+": "+subject.getSubjectTitle());
            newId++;
        }

        //Adding Listeners
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                courseName = GeneralData.getSubjects().get(menuItem.getItemId()).getSubjectTitle();
                courseCode = GeneralData.getSubjects().get(menuItem.getItemId()).getCourseCode();
                binding.subject.setText(courseName);
                return false;
            }
        });

        menu.show();
    }

    void setUpStartTimePicker(){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar calendar =  Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                start = new TimeFormatter(calendar.getTimeInMillis()).getDatabaseTimeFormat();
                //Setting the date on view
                binding.start.setText(new TimeFormatter(calendar.getTimeInMillis()).getTimeFormat1());
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
        dialog.show();
    }
    void setUpEndTimePicker(){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar calendar =  Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                end = new TimeFormatter(calendar.getTimeInMillis()).getDatabaseTimeFormat();
                //Setting date on views
                binding.end.setText(new TimeFormatter(calendar.getTimeInMillis()).getTimeFormat1());
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
        dialog.show();
    }

    void setDayPopUpMenu(){
        PopupMenu menu = new PopupMenu(this,binding.dayDropDown);
        String days[] = getResources().getStringArray(R.array.days);

        for(int i =0 ;i<7; i++){
            menu.getMenu().add(100,i,i,days[i]);
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                day = String.valueOf(menuItem.getItemId());
                binding.day.setText(days[menuItem.getItemId()]);
                return false;
            }
        });
        menu.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}