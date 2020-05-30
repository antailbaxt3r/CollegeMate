package com.antailbaxt3r.collegemate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.ActivityMainBinding;
import com.antailbaxt3r.collegemate.databinding.AppBarMainBinding;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private SharedPrefs prefs;
    private ActivityMainBinding drawerBinding;
    private AppBarMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerBinding = ActivityMainBinding.inflate(getLayoutInflater());
        binding = AppBarMainBinding.inflate(getLayoutInflater());
        setContentView(drawerBinding.getRoot());

        prefs = new SharedPrefs(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null || prefs.getNewUser()){
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
            return;
        }

        setSupportActionBar(binding.toolbar);
        initDrawer();

    }

    private void initDrawer() {
        drawerBinding.navView.setNavigationItemSelectedListener(item -> {
            return false;
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerBinding.drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
