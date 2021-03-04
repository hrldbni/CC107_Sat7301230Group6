package com.example.Group6_SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deardearyjaycabasag.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private ListView listView;
    public DBHelper  myDb = new DBHelper(this);
    Integer userNo;
    String data, TABLE_NAME1, TABLE_NAME2, retrievedId,retrievedUsername, retrievedName, retrievedBirthDate, retrievedEmail, retrievedPassword;
    Button logOut, deleteProfile;
    TextView usernameText, fullNameText, birthDateText, emailText, uidText, dateText;
    EditText contentText;
    FloatingActionButton editDiary;
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
        dateText = (TextView) findViewById(R.id.dateUpdate);
        contentText = (EditText) findViewById(R.id.contentText);
        editDiary = (FloatingActionButton) findViewById(R.id.editDiaryBtn);
        logOut = (Button) findViewById(R.id.logOut);
        deleteProfile = (Button) findViewById(R.id.deleteProfile);

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboard.this);
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to delete this Diary, it can't be undone!")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(UserDashboard.this,
                                        "Cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean var = myDb.deleteData(String.valueOf(userNo));

                                if (var) {
                                    Toast.makeText(UserDashboard.this,
                                            "Diary Logged Out",
                                            Toast.LENGTH_SHORT).show();
                                    Intent homeIntent;
                                    homeIntent = new Intent(UserDashboard.this, HomeActivity.class);
                                    startActivity(homeIntent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboard.this);
                                    builder.setTitle("Warning")
                                            .setMessage("Error Deleting Diary!")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                    builder.show();
                                }





                            }
                        });
                builder.show();

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent;
                homeIntent = new Intent(UserDashboard.this, HomeActivity.class);
                Toast.makeText(UserDashboard.this,
                        "Diary Logged Out",
                        Toast.LENGTH_SHORT).show();
                startActivity(homeIntent);
            }
        });

        editDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this, EditingWindow.class);
                intent.putExtra("diarytext", contentText.getText().toString());
                intent.putExtra("userId", userNo.toString());
                startActivity(intent);
                finish();


            }
        });


        myDb = new DBHelper(this);

        

       Intent intent = getIntent();

        String userId = intent.getStringExtra("userId");
        String name = intent.getStringExtra("username");
        String fullName = intent.getStringExtra("userFullName");
        String birthDate = intent.getStringExtra("birthDate");
        String email = intent.getStringExtra("email");
        String dateLastUpdate = intent.getStringExtra("dateLastUpdate");
        String content = intent.getStringExtra("content");
        String password = intent.getStringExtra("password");

        usernameText.setText(name);
        fullNameText.setText(fullName);
        birthDateText.setText(birthDate);
        emailText.setText(email);
        dateText.setText(" Last Date of Update: " + dateLastUpdate);
        contentText.setText(content);
        userNo = Integer.parseInt(userId);
        uidText.setText("UID : " + userId);


    }
}