package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    TextView currentFundText;
    ImageButton addFundButton;
    TextView textFund;


    private String travelStatus;
    private  String currentFund;
    private int travelBudget;
    String groupTravelCode = "";

    private ProgressDialog progressDialog;

    //Variables under This is for You may want to invite Friends
    RecyclerView inviteFriendRecyclerView;
    AdapterInviteFriend inviteFriendAdapter;
    List<ModelInviteFriend> modelInviteFriendList;
    ModelCurrentTravelId modelCurrentTravelId;
    //End
    //Variable under this is to view all friends joined the travel.
    RecyclerView friendJoinedRecyclerView;
    AdapterFriendsJoined friendsJoinedAdapter;
    List<ModelFriendsJoined> modelFriendsJoinedList;
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
        currentFundText = findViewById(R.id.currentFundText);
        addFundButton = findViewById(R.id.addFundButton);
        textFund = findViewById(R.id.fundText);

        addFundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addFundDialog = new AlertDialog.Builder(TravelDetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_addfund, null);
                EditText dialogCurrentFund = (EditText) mView.findViewById(R.id.dFundToAddText);
                Button addFundButton = (Button) mView.findViewById(R.id.dConfirmAddFund);
                Button deductButton = (Button) mView.findViewById(R.id.dConfirmDeductFund);
                ImageButton closeDialog = (ImageButton) mView.findViewById(R.id.closeDialogButton);


                addFundDialog.setView(mView);
                AlertDialog dialog = addFundDialog.create();

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                addFundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        travelBudget = Integer.valueOf(currentFund) + Integer.parseInt(String.valueOf(dialogCurrentFund.getText()));
                        addFund();
                        dialog.dismiss();
                    }

                });


                deductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TravelDetailsActivity.this, "Deducting", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });

        progressDialog = new ProgressDialog(TravelDetailsActivity.this);
        progressDialog.setMessage("Please wait while retrieving data...");
        modelCurrentTravelId = new ModelCurrentTravelId();
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETTRAVELDETAILS,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject travelDetails = new JSONObject(response);

                            Glide.with(getApplicationContext())
                                    .load(travelDetails.getString("placeimage"))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .placeholder(R.drawable.loader)
                                    .into(userTravelImage);
                            userTravelId.setText("Travel ID: " + travelDetails.getString("travelId"));
                            modelCurrentTravelId.setTravelId(travelDetails.getString("travelId"));
                            userTravelLocation.setText("Place Destination :" + travelDetails.getString("travelDestination"));
                            budgetText.setText("Travel Budget: " + travelDetails.getString("travelFund"));
                            textFund.setText("Php " + travelDetails.getString("currentFund")+".00 ");
                            travelStatus = travelDetails.getString("travelStatus");
                            currentFund = travelDetails.getString("currentFund");
                            travelBudget = travelDetails.getInt("travelFund");
                            groupTravelCode = travelDetails.getString("groupTravelCode");
                            modelCurrentTravelId.setGroupCode(groupTravelCode);
                            viewJoinedFriends();



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

    // List of Friends joined the Travel



    //Viewing all friends

        modelInviteFriendList = new ArrayList<>();
        inviteFriendRecyclerView = (RecyclerView) findViewById(R.id.inviteFriendsRecyclerView);
        inviteFriendRecyclerView.setHasFixedSize(true);
        inviteFriendRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewFriends();
        inviteFriendAdapter = new AdapterInviteFriend(TravelDetailsActivity.this, modelInviteFriendList);
        inviteFriendRecyclerView.setAdapter(inviteFriendAdapter);

    //Viewing all friends Joined
        modelFriendsJoinedList = new ArrayList<>();
        friendJoinedRecyclerView = (RecyclerView) findViewById(R.id.joinTravelFriendsLists);
        friendJoinedRecyclerView.setHasFixedSize(true);
        friendJoinedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void viewJoinedFriends() {

        progressDialog.setMessage("Retrieving Friends, please wait");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETFRIENDSJOINED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject joinedFriendObj = places.getJSONObject(i);

                                int travelId = joinedFriendObj.getInt("travel_id");
                                int userTravelId =  joinedFriendObj.getInt("user_travel_id");
                                String groupTravelCode = joinedFriendObj.getString("group_travel_code");
                                String friendJoinedProfile =  joinedFriendObj.getString("friend_profile");
                                String friendJoinedName = joinedFriendObj.getString("friend_name");
                                modelFriendsJoinedList.add(new ModelFriendsJoined(travelId,userTravelId,groupTravelCode,friendJoinedProfile, friendJoinedName));
                            }

                            friendsJoinedAdapter = new AdapterFriendsJoined(TravelDetailsActivity.this, modelFriendsJoinedList);
                            friendJoinedRecyclerView.setAdapter(friendsJoinedAdapter);

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
                String groupTravelCode = modelCurrentTravelId.getGroupCode().toString();
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                params.put("group_travel_code", groupTravelCode);
                return params;
            }
        };

        RequestHandler.getInstance(TravelDetailsActivity.this).addToRequestQueue(stringRequest);

    }


    private void viewFriends() {
        progressDialog.setMessage("Retrieving Friends, please wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETFRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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

    public void addFund() {
        progressDialog.setMessage("Adding Fund");
        progressDialog.show();
        String travelid = getIntent().getStringExtra("travelId");
        String currentFunds = String.valueOf(travelBudget);
        String travelStats =  travelStatus;


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDFUNDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    TravelDetailsActivity.this).create();


                            alertDialog1.setMessage(obj.getString("message"));
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alertDialog1.show();

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
                params.put("travelid", travelid);
                params.put("currentFund", currentFunds);
                params.put("travelStatus", travelStats);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

}