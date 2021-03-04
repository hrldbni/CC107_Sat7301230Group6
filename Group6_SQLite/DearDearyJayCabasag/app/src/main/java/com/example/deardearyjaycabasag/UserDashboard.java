package com.example.deardearyjaycabasag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private ListView listView;
    public DBHelper  myDb = new DBHelper(this);
    String data, TABLE_NAME1, TABLE_NAME2, retrievedId, retrievedUsername, retrievedName, retrievedBirthDate, retrievedEmail, retrievedPassword;

    TextView usernameText, fullNameText, birthDateText, emailText, uidText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        getSupportActionBar().hide();

        usernameText = (TextView) findViewById(R.id.usernameText);
        fullNameText = (TextView) findViewById(R.id.fullNameText);
        birthDateText = (TextView) findViewById(R.id.birthDateText);
        emailText = (TextView) findViewById(R.id.emailAddressText);
        uidText = (TextView) findViewById(R.id.uidText);
        myDb = new DBHelper(this);

        

       Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String name = intent.getStringExtra("username");

        String fullName = intent.getStringExtra("userFullName");
        String birthDate = intent.getStringExtra("birthDate");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        usernameText.setText(name);
        fullNameText.setText(fullName);
        birthDateText.setText(birthDate);
        emailText.setText(email);

        uidText.setText("UID : " + userId);

        TABLE_NAME2 = "DIARY_DATA";
        myDb.query2 = "select * from " + TABLE_NAME1 + " WHERE DIARYID = \"" + userId + "\";";

        Cursor res = myDb.getData();

        if (res.getCount() == 0) {
            Toast.makeText(UserDashboard.this, "No records", Toast.LENGTH_LONG).show();
            return;
        }

    }
}