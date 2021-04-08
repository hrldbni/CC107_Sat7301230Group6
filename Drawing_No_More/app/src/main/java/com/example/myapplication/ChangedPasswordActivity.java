package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
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

public class ChangedPasswordActivity extends AppCompatActivity {

    Button changePasswordBtn;
    ImageView backBtn;
    EditText oldPassword, newPassword, confirmNewPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_password);
        getSupportActionBar().hide();

        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.newPasswordConfirm);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your Old Password", Toast.LENGTH_SHORT).show();
                } else if (newPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your New Password", Toast.LENGTH_SHORT).show();
                } else if (confirmNewPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your New Password", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please re-enter your password", Toast.LENGTH_SHORT).show();
                    confirmNewPassword.setText("");
                } else {
                    checkPassword();
                }


            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

    }

    private void checkPassword() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_CHANGEPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String userid = String.valueOf(SharedPrefManager.getUid());
                String userOldPassword = oldPassword.getText().toString().trim();
                String userNewPassword = confirmNewPassword.getText().toString().trim();
                params.put("userid", userid);
                params.put("userOldPassword", userOldPassword);
                params.put("userNewPassword", userNewPassword);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}