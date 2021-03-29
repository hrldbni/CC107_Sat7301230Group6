package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Mytravelactivity extends AppCompatActivity {

    ImageButton imgtravel;
    ImageButton imgprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytravelactivity);
        getSupportActionBar().hide();

//button to profile
        imgtravel = (ImageButton) findViewById(R.id.btnprofile);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(Mytravelactivity.this, ProfileActivity.class);
                startActivity(actnew);
            }
        });
//button to home search travel
        imgprofile = (ImageButton) findViewById(R.id.btnhome);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(Mytravelactivity.this, SearchTravel.class);
                startActivity(actnew);
            }
        });

    }
}