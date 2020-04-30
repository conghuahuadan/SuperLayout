package com.chhd.superlayout.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chhd.superlayout.SuperImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SuperImageView iv = findViewById(R.id.iv);
        final CircleImageView civ = findViewById(R.id.civ);
        Glide.with(iv)
                .load("http://attach.bbs.miui.com/forum/201401/11/145825zn1sxa8anrg11gt1.jpg")
                .into(iv);
        Glide.with(civ)
                .load("http://attach.bbs.miui.com/forum/201401/11/145825zn1sxa8anrg11gt1.jpg")
                .into(civ);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
