package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SearchTravel extends AppCompatActivity {

    RecyclerView recyclerView;
    PlacesAdapter adapter;

    List<Places> placesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_search);
        getSupportActionBar().hide();

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