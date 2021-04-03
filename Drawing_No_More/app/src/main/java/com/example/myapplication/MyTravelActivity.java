package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyTravelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersTravelAdapter adapter;
    ImageButton imgtravel;
    ImageButton imgprofile, placeImage;
    List<UserTravels> userTravelsList;



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
                Intent actnew = new Intent(MyTravelActivity.this, ProfileActivity.class);
                startActivity(actnew);
                finish();
            }
        });
//button to home search travel
        imgprofile = (ImageButton) findViewById(R.id.btnhome);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(MyTravelActivity.this, SearchTravel.class);
                startActivity(actnew);
                finish();
            }
        });

        userTravelsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userTravelsList.add(
                new UserTravels(
                        1, "Boracay", "basta", "Hello", "View Details", "Hi"));
        userTravelsList.add(
                new UserTravels(
                        1, "Boracay", "basta", "Hello", "View Details", "Hi"));
        userTravelsList.add(
                new UserTravels(
                        1, "Boracay", "basta", "Hello", "View Details", "Hi"));

        userTravelsList.add(
                new UserTravels(
                        1, "Boracay", "basta", "Hello", "View Details", "Hi"));
        userTravelsList.add(
                new UserTravels(
                        1, "Boracay", "basta", "Hello", "View Details", "Hi"));



        adapter = new UsersTravelAdapter(MyTravelActivity.this, userTravelsList);
        recyclerView.setAdapter(adapter);

    }


}