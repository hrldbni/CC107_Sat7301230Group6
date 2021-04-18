package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class AddFriendActivity extends AppCompatActivity {

    private ImageView backBtn;
    private EditText searchText;

    RecyclerView searchFriendsRecyclerView;
    AdapterAddFriend addFriendAdapter;
    List<ModelAddFriend> modelAddFriendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_friend);
            getSupportActionBar().hide();

            backBtn = (ImageView) findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        modelAddFriendsList = new ArrayList<>();
        searchFriendsRecyclerView = (RecyclerView) findViewById(R.id.searchFriendsRecyclerView);
        searchFriendsRecyclerView.setHasFixedSize(true);
        searchFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(AddFriendActivity.this));

        searchText = (EditText) findViewById(R.id.searchText);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        performSearch();
                        return true;
                    }
                    return false;
                }

                private void performSearch() {

                    final String username = searchText.getText().toString().trim();
                    final String userid = String.valueOf(SharedPrefManager.getUid()).trim();

                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            Constants.URL_SEARCHFRIEND,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    modelAddFriendsList.clear();
                                    try {
                                        JSONArray places = new JSONArray(response);

                                        for (int i = 0; i < places.length(); i++){
                                            JSONObject placesObject = places.getJSONObject(i);

                                            int addFriendUid = placesObject.getInt("uid");
                                            String addFriendUsername = placesObject.getString("username");
                                            String addFriendFullname = placesObject.getString("fullname");
                                            String addFriendProfile = placesObject.getString("profile");
                                            String addFriendCheck = placesObject.getString("addFriendCheck");


                                            modelAddFriendsList.add(new ModelAddFriend(addFriendUid,addFriendFullname,Constants.URL_PROFILE+addFriendProfile, addFriendCheck));



                                        }

                                        addFriendAdapter = new AdapterAddFriend(AddFriendActivity.this,  modelAddFriendsList);
                                        searchFriendsRecyclerView.setAdapter(addFriendAdapter);

                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "Error on JSON"+ e, Toast.LENGTH_LONG).show();
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
                            params.put("userName", username);
                            params.put("email", username);
                            params.put("currentUser_uid", userid);
                            return params;
                        }
                    };

                    RequestHandler.getInstance(AddFriendActivity.this).addToRequestQueue(stringRequest);
                }
            });




    }
}

