package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUpPanel extends AppCompatActivity implements View.OnClickListener {

    private EditText dobText, usernameText, firstnameText, lastnameText, addressText, emailText, passwordText, passwordOne;
    private Button buttonRegister;
    final Calendar myCalendar = Calendar.getInstance();
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_panel);


        usernameText = (EditText) findViewById(R.id.usernameText);
        firstnameText = (EditText) findViewById(R.id.firstnameText);
        lastnameText = (EditText) findViewById(R.id.lastnameText);
        addressText = (EditText) findViewById(R.id.addressText);
        passwordOne = (EditText) findViewById(R.id.passwordOne);
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordConfirm);

        dobText = (EditText) findViewById(R.id.dateOfBirth);
        DatePickerDialog.OnDateSetListener date = this::onDateSet;

        dobText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpPanel.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this) ;

    }

    //Database insertion starts here

    private void registerUser(){

        final String username = usernameText.getText().toString().trim();
        final String firstname = firstnameText.getText().toString().trim();
        final String lastname = lastnameText.getText().toString().trim();
        final String dob = dobText.getText().toString().trim();
        final String address = addressText.getText().toString().trim();
        final String email = emailText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();



        progressDialog.setMessage("Registering user");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("message").equals("User registered")){
                                builder = new AlertDialog.Builder(SignUpPanel.this);
                                builder.setMessage(jsonObject.getString("message"))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                                usernameText.setText("");
                                firstnameText.setText("");
                                lastnameText.setText("");
                                addressText.setText("");
                                dobText.setText("");
                                emailText.setText("");
                                passwordOne.setText("");
                                passwordText.setText("");



                            } else if (jsonObject.getString("message").equals("User unable to register")){
                                builder = new AlertDialog.Builder(SignUpPanel.this);
                                builder.setMessage(jsonObject.getString("message"))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else if (jsonObject.getString("message").equals("User unable to register")){
                                builder = new AlertDialog.Builder(SignUpPanel.this);
                                builder.setMessage(jsonObject.getString("message"))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else if (jsonObject.getString("message").equals("Email already Exist, Please use a different Email")){
                                builder = new AlertDialog.Builder(SignUpPanel.this);
                                builder.setMessage(jsonObject.getString("message"))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                                emailText.setText("");
                            } else if (jsonObject.getString("message").equals("Username already Exist, Please use a different Username")){
                                builder = new AlertDialog.Builder(SignUpPanel.this);
                                builder.setMessage(jsonObject.getString("message"))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                                usernameText.setText("");
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("dob", dob);
                params.put("address", address);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    //Database INsertion ends here




    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobText.setText(sdf.format(myCalendar.getTime()));


    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){

            if (usernameText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input your Username", Toast.LENGTH_SHORT).show();
            }
            else if (lastnameText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input your Lastname", Toast.LENGTH_SHORT).show();
            }
            else if (firstnameText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input your Firstname", Toast.LENGTH_SHORT).show();
            }
            else if (dobText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please select your Date of Birth", Toast.LENGTH_SHORT).show();
            }
            else if (addressText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input your Address", Toast.LENGTH_SHORT).show();
            }
            else if (emailText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please input your Email", Toast.LENGTH_SHORT).show();
            }
            else if (passwordOne.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please Input your password", Toast.LENGTH_SHORT).show();
            }
            else if (passwordText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Please Confirm your Password", Toast.LENGTH_SHORT).show();
            }
            else if (!(passwordOne.getText().toString().equals(passwordText.getText().toString()))){
                Toast.makeText(getApplicationContext(),"Password does not matched", Toast.LENGTH_SHORT).show();
                passwordText.setText("");
            }
            else {
                registerUser();
            }


        }
    }
}