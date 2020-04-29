package com.chhd.superlayout.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = findViewById(R.id.iv);
        Glide.with(iv).load("http://i0.hdslb.com/bfs/article/e64ed895471547ecb86e91e1d287993cbeb8377a.jpg").into(iv);
    }
}
