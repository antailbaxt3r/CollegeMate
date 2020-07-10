package com.antailbaxt3r.collegemate.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.antailbaxt3r.collegemate.R;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.antailbaxt3r.collegemate.adapters.HomeMenuAdapter;
import com.antailbaxt3r.collegemate.databinding.ActivityMainBinding;
import com.antailbaxt3r.collegemate.databinding.AppBarMainBinding;
import com.antailbaxt3r.collegemate.databinding.FragmentTimeTableBinding;
import com.antailbaxt3r.collegemate.dialogs.ProfileDialog;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;
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

       drawerBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId() == R.id.nav_log_out){
                    logout();
               }
               if(item.getItemId() == R.id.nav_profile){
                   showProfileDialog();
               }
               return false;
           }
       });
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

    void showProfileDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment fragment = new ProfileDialog();
        fragment.show(ft,"tag");
    }
    void logout(){
        FirebaseAuth.getInstance().signOut();
        prefs.clearData();
        Intent i = new Intent(this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
