package com.chhd.superlayout.demo;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView iv = findViewById(R.id.iv);
        Glide.with(iv).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2539191794,136527412&fm=26&gp=0.jpg").into(iv);

        ImageView iv1 = findViewById(R.id.iv1);
        Glide.with(iv1).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2539191794,136527412&fm=26&gp=0.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iv1);
    }
}
