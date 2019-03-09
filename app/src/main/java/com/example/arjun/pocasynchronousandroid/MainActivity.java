package com.example.arjun.pocasynchronousandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    public static final String url = "https://tineye.com/images/widgets/mona.jpg";
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
//        Observable<Bitmap> bitmap=Observable.just(gettheImage()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//        bitmap.subscribe(s->imageView.setImageBitmap(s));

        Observable.defer(new Callable<ObservableSource<? extends Bitmap>>() {
            @Override
            public ObservableSource<? extends Bitmap> call() throws Exception {
                Bitmap b=gettheImage();
                return Observable.just(b);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s->imageView.setImageBitmap(s));
    }


    public Bitmap gettheImage() {
        URL newurl = null;
        try {
            newurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
