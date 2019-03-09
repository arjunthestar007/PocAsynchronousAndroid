package com.example.arjun.pocasynchronousandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    public static final String url = "https://tineye.com/images/widgets/mona.jpg";
    private static final String TAG = "ExampleThread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        Log.d(TAG, "TAG: "+Thread.currentThread().getName());

        // creating new thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "TAG: "+Thread.currentThread().getName());
                URL newurl = null;
                try {
                    newurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap finalBitmap = bitmap;
                // access ui thread from background
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "TAG: "+Thread.currentThread().getName());
                        imageView.setImageBitmap(finalBitmap);

                    }
                });


            }
        });
        thread.start();


    }
}
