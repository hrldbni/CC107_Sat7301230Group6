package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText userEmail;
    Button sendToEmail;
    ImageView backBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        userEmail = findViewById(R.id.forgotPasswordEmail);
        sendToEmail = findViewById(R.id.forgotPasswordSend);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sendToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( userEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Enter your Email Address",Toast.LENGTH_SHORT).show();
                }else {
                    if (userEmail.getText().toString().trim().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(),"Please wait while sending",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address\nPlease Check your email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}