package com.antailbaxt3r.collegemate.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.apiControllers.LibraryCtrl;
import com.antailbaxt3r.collegemate.data.GeneralData;
import com.antailbaxt3r.collegemate.databinding.ActivityAddLibraryBinding;
import com.antailbaxt3r.collegemate.databinding.RecyclerSubjectBinding;
import com.antailbaxt3r.collegemate.models.Library;
import com.antailbaxt3r.collegemate.models.LibraryPostResponseModel;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AddLibraryActivity extends AppCompatActivity {
    ActivityAddLibraryBinding binding;
    SharedPrefs prefs;
    Library library = new Library();
    byte[] fileData;

    //Request codes
    int GET_PDF = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_PDF){
            try {
                fileData = getBytes(getContentResolver().openInputStream(data.getData()));
                binding.pdf.setText("Pdf file selected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new SharedPrefs(this);

        binding.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pdfIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pdfIntent.setType("application/pdf");
                startActivityForResult(pdfIntent,GET_PDF);
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTexts();
                uploadFile();
            }
        });
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    void setTexts(){
        library.setName(binding.name.getText().toString());
        library.setDescription(binding.description.getText().toString());
    }
    void uploadFile(){
        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();
        binding.progress.setVisibility(View.VISIBLE);
        LibraryCtrl.postLibrary(library, prefs.getToken(), fileData, new Callback<LibraryPostResponseModel>() {
            @Override
            public void onResponse(Call<LibraryPostResponseModel> call, Response<LibraryPostResponseModel> response) {
                if(response.isSuccessful()){
                    binding.progress.setVisibility(View.GONE);
                    GeneralData.libraries.add(response.body().getLibrary());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LibraryPostResponseModel> call, Throwable t) {

            }
        });
    }
}