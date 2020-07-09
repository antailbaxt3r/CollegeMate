package com.antailbaxt3r.collegemate.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.antailbaxt3r.collegemate.R;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.antailbaxt3r.collegemate.adapters.HomeMenuAdapter;
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
        setContentView(drawerBinding.getRoot());
        binding = drawerBinding.appBar;

        prefs = new SharedPrefs(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null || prefs.getNewUser()){
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
            return;
        }

        Log.i("ID TOKEN",prefs.getToken());

        setSupportActionBar(binding.toolbar);
        initDrawer();

        initViewPager();
    }

    private void initViewPager(){
        HomeMenuAdapter adapter = new HomeMenuAdapter(getSupportFragmentManager(),0);
        binding.viewpager.setAdapter(adapter);
        binding.bottomNavBar.setupWithViewPager(binding.viewpager);
    }

    private void initDrawer() {

       ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerBinding.homeDrawer, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerBinding.homeDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Opening Drawer
        drawerBinding.homeDrawer.openDrawer(Gravity.LEFT);
        return super.onSupportNavigateUp();
    }
}
