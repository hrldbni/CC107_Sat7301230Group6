package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
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

import static java.sql.Types.NULL;

public class InviteFriendActivity extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView inviteFriendRecyclerView;
    AdapterInviteFriend inviteFriendAdapter;
    List<ModelInviteFriend> modelInviteFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        getSupportActionBar().hide();

        storeTravelId(Integer.valueOf(getIntent().getStringExtra("travel_id")));


        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        modelInviteFriendList = new ArrayList<>();
        inviteFriendRecyclerView = (RecyclerView) findViewById(R.id.inviteFriendsRecyclerView);
        inviteFriendRecyclerView.setHasFixedSize(true);
        inviteFriendRecyclerView.setLayoutManager(new LinearLayoutManager(InviteFriendActivity.this));

        viewFriends();
        //modelInviteFriendList.add(new ModelInviteFriend(1,"Jason Marsh","https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTX4qAOZgCtMV8Y6MfK3P8bLIlIPVvbvIWm66jISmkSRcK-csis"));
        inviteFriendAdapter = new AdapterInviteFriend(InviteFriendActivity.this, modelInviteFriendList);
        inviteFriendRecyclerView.setAdapter(inviteFriendAdapter);


    }

    private void storeTravelId(int travelId){
        SharedPreferences mSharedPreferences = getSharedPreferences("TravelId", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("travelId", travelId);
        mEditor.apply();
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

                            inviteFriendAdapter = new AdapterInviteFriend(InviteFriendActivity.this, modelInviteFriendList);
                            inviteFriendRecyclerView.setAdapter(inviteFriendAdapter);

                        } catch (JSONException e) {
                            Toast.makeText(InviteFriendActivity.this, "Error JSON "+ e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InviteFriendActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

        RequestHandler.getInstance(InviteFriendActivity.this).addToRequestQueue(stringRequest);

    }
}