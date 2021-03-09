package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_panel);

        TextView signUpNow = (TextView) findViewById(R.id.signUpNow);
        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent;
                newIntent = new Intent(LoginPanel.this, NavigationDrawer.class);
                startActivity(newIntent);
                finish();
            }
        });



    }
}