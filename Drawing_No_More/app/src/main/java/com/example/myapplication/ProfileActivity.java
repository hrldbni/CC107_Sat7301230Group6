package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView userFullNameText;
    TextView userEmailText;
    TextView userUidText, userText;
    TextView userNameText;
    Button logoutBtn;
    LinearLayout travelBtn, mytravel;

    ImageButton imgtravel;
    ImageButton imgprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

//button to travel activity
        imgtravel = (ImageButton) findViewById(R.id.btntravel);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(ProfileActivity.this, Mytravelactivity.class);
                startActivity(actnew);
            }
        });
//button to search travel
        imgprofile = (ImageButton) findViewById(R.id.btnhome);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(ProfileActivity.this, SearchTravel.class);
                startActivity(actnew);
            }
        });

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginPanel.class));
        }

       userFullNameText = (TextView) findViewById(R.id.fullnameText);
        userEmailText = (TextView) findViewById(R.id.userEmailText);
        userUidText = (TextView) findViewById(R.id.uidText);
        userText = (TextView) findViewById(R.id.userText);
        userNameText = (TextView) findViewById(R.id.usernameText);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        userFullNameText.setText(SharedPrefManager.getInstance(this).getFullname());
        userEmailText.setText(SharedPrefManager.getInstance(this).getUserEmail());
        userUidText.setText(String.valueOf(SharedPrefManager.getInstance(this).getUid()));
        userText.setText(SharedPrefManager.getInstance(this).getUsername());

        travelBtn = (LinearLayout) findViewById(R.id.travelBtn);
        travelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchTravel.class));
                finish();
            }
        });

        mytravel = (LinearLayout) findViewById(R.id.mytravels);
        mytravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Mytravelactivity.class));
                finish();
            }
        });



    }

    public void logout(View view) {
        SharedPrefManager.getInstance(this).loggedOut();
        startActivity(new Intent(getApplicationContext(), LoginPanel.class));
        finish();
        Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
    }
}