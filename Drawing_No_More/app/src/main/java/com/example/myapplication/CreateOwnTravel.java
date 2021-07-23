package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateOwnTravel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner travelType;
    ImageView backBtn;
    FloatingActionButton addToTravel;
    String selectedTravelType;
    EditText travelTitleText, travelAddress1,travelAddress2, travelDescription, travelBudget, travelDate;
    String travelTypeText;
    Button saveButton;
    TextView privateText;

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

        privateText = findViewById(R.id.privateText);
        privateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateOwnTravel.this, "Private Travels means, it cannot be seen by other travelers.", Toast.LENGTH_SHORT).show();
            }
        });
        travelTitleText = findViewById(R.id.travelTitle);
        travelAddress1 = findViewById(R.id.travelLocationFirstLine);
        travelAddress2 = findViewById(R.id.travelLocationSecondLine);
        travelDescription = findViewById(R.id.travelDescription);
        travelBudget = findViewById(R.id.travelBudget);
        travelDate = findViewById(R.id.travelDate);
        travelTypeText = String.valueOf(selectedTravelType);
        saveButton = findViewById(R.id.saveTravelButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello "+ travelTitleText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        selectedTravelType = adapterView.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select the Type of Travel", Toast.LENGTH_SHORT).show();
    }
}