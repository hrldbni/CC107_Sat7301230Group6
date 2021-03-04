package com.example.Group6_SQLite;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deardearyjaycabasag.R;


public class HomeActivity extends AppCompatActivity {

    Button signUp, logIn;
    EditText recoveryBtn;
    EditText username, userPassword;
    public DBHelper  myDb = new DBHelper(this);
    String data, TABLE_NAME1, retrievedId, retrievedUsername, retrievedName, retrievedBirthDate, retrievedEmail, retrievedPassword, retrievedDate, retrievedContent;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();



        logIn = (Button) findViewById(R.id.logIn);
        username = (EditText) findViewById(R.id.username);
        userPassword = (EditText) findViewById(R.id.userPassword);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TABLE_NAME1 = "USER_DATA";
                myDb.query = "select * from " + TABLE_NAME1 + " WHERE USERNAME = \"" + username.getText().toString() + "\" AND PASSWORD = \"" + userPassword.getText().toString() + "\";";

                Cursor res = myDb.getData();

                if (res.getCount() == 0) {
                    Toast.makeText(HomeActivity.this, "No records", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer idBuffer = new StringBuffer();
                StringBuffer userNameBuffer = new StringBuffer();
                StringBuffer nameBuffer = new StringBuffer();
                StringBuffer dobBuffer = new StringBuffer();
                StringBuffer emailBuffer = new StringBuffer();
                StringBuffer passwordBuffer = new StringBuffer();
                StringBuffer dateBuffer = new StringBuffer();
                StringBuffer contentBuffer = new StringBuffer();


                    while (res.moveToNext()){
                        idBuffer.append(res.getString(0));
                        retrievedId = idBuffer.toString();

                        userNameBuffer.append(res.getString(1));
                        retrievedUsername = userNameBuffer.toString();

                        nameBuffer.append(res.getString(2));
                        retrievedName = nameBuffer.toString();

                        dobBuffer.append(res.getString(3));
                        retrievedBirthDate = dobBuffer.toString();

                        emailBuffer.append(res.getString(4));
                        retrievedEmail = emailBuffer.toString();

                        passwordBuffer.append(res.getString(5));
                        retrievedPassword = passwordBuffer.toString();

                        dateBuffer.append(res.getString(6));
                        retrievedDate = dateBuffer.toString();

                        contentBuffer.append(res.getString(7));
                        retrievedContent = contentBuffer.toString();
                    }

                if (username.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Warning")
                            .setMessage("Some fields are empty!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                } else {
                    boolean var = myDb.checkUser(username.getText().toString(), userPassword.getText().toString());

                    if (var){

                        Toast.makeText(HomeActivity.this, "Welcome to your Diary", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, UserDashboard.class);
                        intent.putExtra("userId", retrievedId);
                        intent.putExtra("username", retrievedUsername);
                        intent.putExtra("userFullName", retrievedName);
                        intent.putExtra("birthDate", retrievedBirthDate);
                        intent.putExtra("email", retrievedEmail);
                        intent.putExtra("password", retrievedPassword);
                        intent.putExtra("dateLastUpdate", retrievedDate);
                        intent.putExtra("content", retrievedContent);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(HomeActivity.this, "Password is Incorrect", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

        signUp = (Button) findViewById(R.id.sign_up_btn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent;
                signUpIntent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                finish();
            }
        });




    }
}