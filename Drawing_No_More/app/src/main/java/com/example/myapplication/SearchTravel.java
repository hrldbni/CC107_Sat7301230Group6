package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class SearchTravel extends AppCompatActivity {

    RecyclerView recyclerView;
    PlacesAdapter adapter;

    List<Places> placesList;

    ImageButton imgtravel;
    ImageButton imgprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_search);
        getSupportActionBar().hide();

//button to travel activity
        imgtravel = (ImageButton) findViewById(R.id.btntravel);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(SearchTravel.this, MyTravelActivity.class);
                startActivity(actnew);
            }
        });
//button to profile
        imgprofile = (ImageButton) findViewById(R.id.btnprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(SearchTravel.this, ProfileActivity.class);
                startActivity(actnew);
            }
        });


        placesList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        placesList.add(
                new Places(
                        1,
                        "Hello World",
                        "Hello World is a text for ",
                        4.2,
                        "Start Drawing",
                        R.drawable.baguios));

        placesList.add(
                new Places(
                        1,
                        "Hello World",
                        "Hello World is a text for ",
                        4.2,
                        "Start Drawing",
                        R.drawable.baguios));
        placesList.add(
                new Places(
                        1,
                        "Hello World",
                        "Hello World is a text for ",
                        4.2,
                        "Start Drawing",
                        R.drawable.baguios));

        adapter = new PlacesAdapter(this, placesList);
        recyclerView.setAdapter(adapter);

    }
}