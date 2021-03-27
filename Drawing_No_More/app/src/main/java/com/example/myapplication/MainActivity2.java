package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity2 extends AppCompatActivity {

    ImageButton imgtravel;
    ImageButton imgprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle("DRAWING NO MORE");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00A6E4")));
//button to travel
        imgtravel = (ImageButton) findViewById(R.id.btntravel);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(MainActivity2.this, travel.class);
                startActivity(actnew);
            }
        });
//button to profile
        imgprofile = (ImageButton) findViewById(R.id.btnprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(MainActivity2.this, ProfileActivity.class);
                startActivity(actnew);
            }
        });

    }
}