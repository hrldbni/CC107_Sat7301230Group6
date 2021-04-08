package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                        builder.setTitle("Warning");
                        builder.setMessage("Upon confirming \nThe new recovery Password will be sent to this Email: " + userEmail.getText().toString());
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendRecoveryPass();
                                Toast.makeText(getApplicationContext(), "Please wait for an email to be sent to you", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when No button clicked
                                Toast.makeText(getApplicationContext(),
                                        "Cancelled",Toast.LENGTH_SHORT).show();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        sendRecoveryPass();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address\nPlease Check your email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    private void sendRecoveryPass() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FORGOTPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String email = userEmail.getText().toString().trim();
                params.put("email", email);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}