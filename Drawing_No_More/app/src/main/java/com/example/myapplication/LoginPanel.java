package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameText, passwordText;
    private Button loginBtn;
    private TextView textLogin, forgotPasswordBtn;
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

        Animation animation = AnimationUtils.loadAnimation(LoginPanel.this, R.anim.fadein);


        usernameText = (EditText) findViewById(R.id.emailAddress);
        usernameText.setAnimation(animation);
        passwordText = (EditText) findViewById(R.id.userPassword);
        passwordText.setAnimation(animation);
        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setAnimation(animation);
        textLogin = (TextView) findViewById(R.id.LoginText);
        textLogin.setAnimation(animation);

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });

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
                                                    String.valueOf(obj.getString("fullname")),
                                                    String.valueOf(obj.getString("dob")),
                                                    String.valueOf(Constants.URL_PROFILE+obj.getString("profile")) );

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
            if (usernameText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter email or username", Toast.LENGTH_LONG).show();
            } else if (passwordText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
            } else {
                userLogin();
            }

        }
    }
}