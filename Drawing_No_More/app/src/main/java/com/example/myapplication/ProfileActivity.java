package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView userFullNameText;
    TextView userEmailText;
    TextView userUidText, userText;
    TextView userNameText;
    Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("PROFILE");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#58E0BF")));

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



    }

    public void logout(View view) {
        SharedPrefManager.getInstance(this).loggedOut();
        startActivity(new Intent(getApplicationContext(), LoginPanel.class));
        finish();
        Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
    }
}