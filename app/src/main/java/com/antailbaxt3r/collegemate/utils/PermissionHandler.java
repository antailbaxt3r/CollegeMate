package com.antailbaxt3r.collegemate.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.security.Permission;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHandler {
    Context context;
    private int STORAGE = 100;

    public PermissionHandler(Context context){
        this.context = context;
    }

    public void askStoragePermissions(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
        || ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE);
        }

    }
}
