package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
//SPLASH SCREEN
    ImageView drawingText;
    com.airbnb.lottie.LottieAnimationView img;
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        img = (com.airbnb.lottie.LottieAnimationView) findViewById(R.id.travelLogo);
        drawingText = (ImageView) findViewById(R.id.drawingtext);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade);
        img.startAnimation(animation);
        drawingText.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent;
                homeIntent = new Intent(MainActivity.this, LoginPanel.class);

                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}