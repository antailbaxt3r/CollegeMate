package com.antailbaxt3r.collegemate.tasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Paths;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

public class AsyncTaskDownloadPdf extends AsyncTask<Object, String, Object> {

    String path;
    Context context;

    public AsyncTaskDownloadPdf(String path, Context context) {
        this.path = path;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Object doInBackground(Object... objects) {
        Log.i("Pdf Path",path);
        initialize();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {


        super.onPostExecute(o);
    }

    void downloadFile(File pdfFile){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void initialize(){
        File file = new File(Environment.getExternalStorageDirectory(),"CollegeMate");
        if(!file.exists()){
            file.mkdir();
        }

        //Checking previous existence
        String filename = Paths.get(path).getFileName().toString();
        File pdfFile = new File(file,filename);
        downloadFile(pdfFile);
        showFile(pdfFile);
        /*if(pdfFile.exists()){
            showFile(pdfFile);
        }else{

        }  */
    }

    void showFile(File pdfFile){
        Uri contentUri = FileProvider.getUriForFile(context,"com.antailbaxt3r.collegemate.provider",pdfFile);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(contentUri,"application/pdf");
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);
    }




}
