package com.example.group6_age;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    EditText birthDayText, firstName, lastName;
    ImageButton calendarBtn;
    Button computeAgeBtn;
    int birthMonth;
    int birthYear;
    int birthDay;
    int age;
    int dateToday, monthNow, yearNow;
    String message;



    Calendar c;
    DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthDayText = (EditText) findViewById(R.id.dobText);

        calendarBtn = (ImageButton) findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        birthDayText.setText((mMonth+1) + "/" + mDay + "/" + mYear);
                        birthMonth = mMonth + 1;
                        birthYear = mYear;
                        birthDay = mDay;
                    }
                }, month, day, year);
                dpd.show();
            }
        });

        computeAgeBtn = (Button) findViewById(R.id.computeAgeBtn);

        computeAgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = (EditText) findViewById(R.id.firstNameText);
                lastName = (EditText) findViewById(R.id.lastNameText);
                String fullName = firstName.getText() + " " + lastName.getText() + " ";
                c = Calendar.getInstance();
                monthNow = c.get(Calendar.MONTH);
                yearNow = c.get(Calendar.YEAR);
                dateToday = c.get(Calendar.DAY_OF_MONTH);


                if(dateToday == birthDay){

                    if (birthMonth == (monthNow + 1) ){
                        age = yearNow - birthYear;


                    }
                    if (birthMonth < (monthNow + 1)){
                        age = yearNow - birthYear;


                    }
                    if (birthMonth > (monthNow + 1)){
                        age = yearNow - birthYear;
                        age = age - 1;


                    }

                }

                if (dateToday > birthDay){



                    if (birthMonth == (monthNow + 1)){
                        age = yearNow - birthYear;

                    }
                    if (birthMonth < (monthNow + 1)){

                        age = yearNow - birthYear;

                    }
                    if (birthMonth > (monthNow + 1)){
                        //Halimbawa March tas Feb ngayon
                        age = yearNow - birthYear;
                        age = age - 1;

                    }
                }

                if (dateToday < birthDay){


                    if (birthMonth == (monthNow + 1)){

                        age = yearNow - birthYear;
                        age = age - 1;

                    }
                    if (birthMonth < (monthNow + 1)){

                        age = yearNow - birthYear;

                    }
                    if (birthMonth > (monthNow + 1)){

                        age = yearNow - birthYear;
                        age = age - 1;

                    }
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Attention!");


                if (age < 18){
                    builder.setMessage(fullName + "cannot VOTE!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Notified", Toast.LENGTH_SHORT).show();
                        }
                    }); builder.show();

                } else {
                    builder.setMessage(fullName + " " + "can VOTE!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Congratulations", Toast.LENGTH_SHORT).show();
                        }
                    }); builder.show();

                }


            }
        });






    }
}