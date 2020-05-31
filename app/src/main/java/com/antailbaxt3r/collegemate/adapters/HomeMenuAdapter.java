package com.antailbaxt3r.collegemate.adapters;

import com.antailbaxt3r.collegemate.fragments.DashboardFragment;
import com.antailbaxt3r.collegemate.fragments.RemindersFragment;
import com.antailbaxt3r.collegemate.fragments.TimeTableFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeMenuAdapter extends FragmentPagerAdapter {


    public HomeMenuAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new DashboardFragment();
        }else if(position ==1){
            return new TimeTableFragment();
        }else if(position ==2){
            return new RemindersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
