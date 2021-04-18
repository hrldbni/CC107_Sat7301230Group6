package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateOwnTravel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner travelType;
    ImageView backBtn;
    FloatingActionButton addToTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_own_travel);
        getSupportActionBar().hide();

        travelType = findViewById(R.id.travelType);
        travelType.setOnItemSelectedListener(this);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addToTravel = findViewById(R.id.addToTravel);
        addToTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent;
                newintent = new Intent(CreateOwnTravel.this, SetTravelActivity.class);
                startActivity(newintent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select the Type of Travel", Toast.LENGTH_SHORT).show();
    }
}