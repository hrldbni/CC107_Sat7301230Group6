package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelDetailsActivity extends AppCompatActivity {

    Dialog inviteFriendDialog;
    ImageView backBtn;

    ImageView userTravelImage;
    TextView userTravelId;
    TextView userTravelLocation;
    TextView budgetText;
    private ProgressDialog progressDialog;

    //Variables under This is for You may want to invite Friends
    RecyclerView inviteFriendRecyclerView;
    AdapterInviteFriend inviteFriendAdapter;
    List<ModelInviteFriend> modelInviteFriendList;
    //End



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        getSupportActionBar().hide();

        String travelId = getIntent().getStringExtra("travelId");

        inviteFriendDialog = new Dialog(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //Code to get info on a certain travel
        userTravelImage = findViewById(R.id.userTravelImage);
        userTravelId = findViewById(R.id.userTravelId);
        userTravelLocation = findViewById(R.id.userTravelLocation);
        budgetText = findViewById(R.id.budgetText);


        progressDialog = new ProgressDialog(TravelDetailsActivity.this);
        progressDialog.setMessage("Please wait while retrieving data...");

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETTRAVELDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject travelDetails = new JSONObject(response);

                            Glide.with(getApplicationContext())
                                    .load(travelDetails.getString("placeimage"))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.loader)
                                    .into(userTravelImage);
                            userTravelId.setText("Travel ID: " + travelDetails.getString("travelId"));
                            userTravelLocation.setText("Place Destination :" + travelDetails.getString("travelDestination"));
                            budgetText.setText("Travel Budget: " + travelDetails.getString("travelFund"));



                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(TravelDetailsActivity.this, "Error JSON " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TravelDetailsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("travelId", travelId);
                return params;
            }
        };

        RequestHandler.getInstance(TravelDetailsActivity.this).addToRequestQueue(stringRequest);

    // End of View Travel Details

    //Viewing all friends

        modelInviteFriendList = new ArrayList<>();
        inviteFriendRecyclerView = (RecyclerView) findViewById(R.id.inviteFriendsRecyclerView);
        inviteFriendRecyclerView.setHasFixedSize(true);
        inviteFriendRecyclerView.setLayoutManager(new LinearLayoutManager(TravelDetailsActivity.this));
        viewFriends();
        inviteFriendAdapter = new AdapterInviteFriend(TravelDetailsActivity.this, modelInviteFriendList);
        inviteFriendRecyclerView.setAdapter(inviteFriendAdapter);


    }
    private void viewFriends() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETFRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int friendId = placesObject.getInt("friend_id");
                                int friend_userId = placesObject.getInt("friend_userId");
                                String friendName = placesObject.getString("friendName");
                                String friendImage = placesObject.getString("friendImage");

                                modelInviteFriendList.add(new ModelInviteFriend(friend_userId,friendName ,friendImage));

                            }

                            inviteFriendAdapter = new AdapterInviteFriend(TravelDetailsActivity.this, modelInviteFriendList);
                            inviteFriendRecyclerView.setAdapter(inviteFriendAdapter);

                        } catch (JSONException e) {
                            Toast.makeText(TravelDetailsActivity.this, "Error JSON "+ e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TravelDetailsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String userid = String.valueOf(SharedPrefManager.getUid());

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                return params;
            }
        };

        RequestHandler.getInstance(TravelDetailsActivity.this).addToRequestQueue(stringRequest);

    }

}