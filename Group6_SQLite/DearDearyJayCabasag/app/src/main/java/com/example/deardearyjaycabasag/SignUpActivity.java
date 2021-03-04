package com.example.deardearyjaycabasag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    EditText username, firstName, lastName, email, password, passwordTwo;
    final Calendar myCalendar = Calendar.getInstance();
    EditText birthDate;
    Button returnLogin, signUpNow;
    public DBHelper myDb;
    String data, TABLE_NAME1, retrievedId, userId;


    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        returnLogin = (Button) findViewById(R.id.returnLogin);
        signUpNow = (Button) findViewById(R.id.signUpNow);
        username = (EditText) findViewById(R.id.userName);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.emailAddressText);
        password = (EditText) findViewById(R.id.userPasswordOne);
        passwordTwo = (EditText) findViewById(R.id.userPasswordTwo);
        myDb = new DBHelper(this);

        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().toString().isEmpty() || firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty()|| birthDate.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Warning")
                            .setMessage("Some fields are empty!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }else if(password.getText().toString().equals((passwordTwo.getText().toString()))){

                    boolean var = myDb.registerUser( username.getText().toString(), firstName.getText().toString() +" "+lastName.getText().toString(), birthDate.getText().toString(), email.getText().toString(), passwordTwo.getText().toString());
                    if (var) {
                        Toast.makeText(SignUpActivity.this, "User Registered Succcessully", Toast.LENGTH_LONG).show();

                        //Start of retriving data


                        
                        //End of retriving data



                        Intent homeIntent;
                        homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    }


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Warning")
                    .setMessage("Password does not Match! Please re-enter your Password!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            }
        });
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent;
                homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });


         birthDate = (EditText) findViewById(R.id.birthDateText);
        DatePickerDialog.OnDateSetListener date = this::onDateSet;

        birthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthDate.setText(sdf.format(myCalendar.getTime()));


    }






}