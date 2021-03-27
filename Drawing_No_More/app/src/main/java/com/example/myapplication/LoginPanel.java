package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginPanel extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameText, passwordText;
    private Button loginBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_panel);
        getSupportActionBar().hide();
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        TextView signUpNow = (TextView) findViewById(R.id.signUpNow);
        signUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent;
                newIntent = new Intent(LoginPanel.this, SignUpPanel.class);
                startActivity(newIntent);
                finish();
            }
        });

        usernameText = (EditText) findViewById(R.id.emailAddress);
        passwordText = (EditText) findViewById(R.id.userPassword);
        loginBtn = (Button) findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
    }

    private void userLogin(){
        final String username = usernameText.getText().toString().trim();
        final String email = usernameText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                                if (obj.getBoolean("error") == false){

                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLogin(Integer.parseInt(obj.getString("uid")),
                                                    String.valueOf(obj.getString("username")),
                                                    String.valueOf(obj.getString("email")),
                                                    String.valueOf(obj.getString("fullname")));

                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                }


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
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onClick(View view) {
        if (view == loginBtn){
            userLogin();
        }
    }
}