package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    LinearLayout travelBtn, mytravel, profileContainer, personalInfoContainer, myEmails, myBirth, myFunds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("PROFILE");

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginPanel.class));
        }

        Animation animation = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fadein);
        Animation animation2 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.slide_down);
        Animation animation3 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.fadein);


        profileContainer = (LinearLayout) findViewById(R.id.profileContainer);
        profileContainer.setAnimation(animation);
        personalInfoContainer = (LinearLayout) findViewById(R.id.personalInfoContainer);
        personalInfoContainer.setAnimation(animation2);
        myEmails = (LinearLayout) findViewById(R.id.myemails);
        myEmails.setAnimation(animation);
        myBirth = (LinearLayout) findViewById(R.id.mybirth);
        myBirth.setAnimation(animation);
        myFunds = (LinearLayout) findViewById(R.id.myfunds);
        myFunds.setAnimation(animation);


        userFullNameText = (TextView) findViewById(R.id.fullnameText);
        userEmailText = (TextView) findViewById(R.id.userEmailText);
        userUidText = (TextView) findViewById(R.id.uidText);
        userText = (TextView) findViewById(R.id.userText);
        userNameText = (TextView) findViewById(R.id.usernameText);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setAnimation(animation);

        userFullNameText.setText(SharedPrefManager.getInstance(this).getFullname());
        userEmailText.setText(SharedPrefManager.getInstance(this).getUserEmail());
        userUidText.setText(String.valueOf(SharedPrefManager.getInstance(this).getUid()));
        userText.setText(SharedPrefManager.getInstance(this).getUsername());

        travelBtn = (LinearLayout) findViewById(R.id.travelBtn);
        travelBtn.setAnimation(animation);
        travelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchTravel.class));
                finish();
            }
        });

        mytravel = (LinearLayout) findViewById(R.id.mytravels);
        mytravel.setAnimation(animation);
        mytravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyTravelActivity.class));
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