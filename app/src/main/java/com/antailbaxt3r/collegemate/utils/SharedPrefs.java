package com.antailbaxt3r.collegemate.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String email = "email", name = "name", token = "token", newUser = "newUser";

    @SuppressLint("CommitPrefEdits")
    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, null);
    }

    public void saveEmail(String key){
        editor.putString(email, key);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(name, null);
    }

    public void saveName(String key){
        editor.putString(name, key);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(token, null);
    }

    public void saveToken(String key){
        editor.putString(token, key);
        editor.commit();
    }

    public boolean getNewUser() {
        return sharedPreferences.getBoolean(newUser, true);
    }

    public void saveNewUser(boolean key){
        editor.putBoolean(newUser, key);
        editor.commit();
    }
}
