package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTravelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersTravelAdapter adapter;
    ImageButton imgtravel;
    ImageButton imgprofile, placeImage;
    List<UserTravels> userTravelsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytravelactivity);
        getSupportActionBar().hide();

//button to profile
        imgtravel = (ImageButton) findViewById(R.id.btnprofile);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(MyTravelActivity.this, ProfileActivity.class);
                startActivity(actnew);
                finish();
            }
        });
//button to home search travel
        imgprofile = (ImageButton) findViewById(R.id.btnhome);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(MyTravelActivity.this, SearchTravel.class);
                startActivity(actnew);
                finish();
            }
        });

        userTravelsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewUserTravels();


    }

    private void viewUserTravels() {

        String userid = String.valueOf(SharedPrefManager.getInstance(this).getUid());

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETUSERTRAVELS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject userTravelsObject = places.getJSONObject(i);

                                String travelid =  userTravelsObject.getString("travelId");
                                String travelDate = userTravelsObject.getString("travelDate");
                                String travelFund = userTravelsObject.getString("travelFund");
                                String currentFund = userTravelsObject.getString("currentFund");
                                String travelDestination = userTravelsObject.getString("travelDestination");
                                String travelPlaceImage = userTravelsObject.getString("placeimage");

                                userTravelsList.add(
                                        new UserTravels(
                                                Integer.parseInt(travelid), travelDestination, travelDate, currentFund, travelFund, travelPlaceImage));

                            }

                            adapter = new UsersTravelAdapter(MyTravelActivity.this, userTravelsList);
                            recyclerView.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error Jason" + e, Toast.LENGTH_LONG).show();
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
                params.put("userTravelId", userid);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }


}