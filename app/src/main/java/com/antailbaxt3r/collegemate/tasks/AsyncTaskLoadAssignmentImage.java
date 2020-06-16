package com.antailbaxt3r.collegemate.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncTaskLoadAssignmentImage extends AsyncTask<Object,Object,Object> {
    ImageView imageView;
    Context context;
    String imagePath;
    Bitmap bitmap;

    public AsyncTaskLoadAssignmentImage(ImageView imageView, Context context, String imagePath) {
        this.imageView = imageView;
        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            URL url = new URL(imagePath);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        imageView.setImageBitmap(bitmap);
    }
}
