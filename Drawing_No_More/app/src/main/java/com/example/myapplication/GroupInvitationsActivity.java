package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupInvitationsActivity extends AppCompatActivity {

    ImageButton backBtn;

    RecyclerView groupInvitationsRecyclerView;
    AdapterGroupInvitations groupInvitationsAdapter;
    List<ModelGroupInvitations> modelGroupInvitationsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_invitations);
        getSupportActionBar().hide();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modelGroupInvitationsList = new ArrayList<>();
        groupInvitationsRecyclerView = (RecyclerView) findViewById(R.id.groupInvitationRecyclerView);
        groupInvitationsRecyclerView.setHasFixedSize(true);
        groupInvitationsRecyclerView.setLayoutManager(new LinearLayoutManager(GroupInvitationsActivity.this));

        showGroupInvitations();

        //modelGroupInvitationsList.add(new ModelGroupInvitations(4,"87271995GC98","9","https://www.phbus.com/product_images/uploaded_images/tagaytay-covid19-update-open-tourists.jpg","98"));

    }

    private void showGroupInvitations() {

        String userid = String.valueOf(SharedPrefManager.getUid()).trim();;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETTRAVELINVITATIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int reqId = Integer.parseInt(jsonObject.getString("Request Id"));
                            String groupTravelId = jsonObject.getString("Group Travel Code");
                            String groupTravelAdmin = jsonObject.getString("Group Travel Admin");
                            String travelId = jsonObject.getString("Travel Id");

                            modelGroupInvitationsList.add(new ModelGroupInvitations(reqId,groupTravelId,groupTravelAdmin,userid,travelId));

                            groupInvitationsAdapter = new AdapterGroupInvitations(GroupInvitationsActivity.this, modelGroupInvitationsList);
                            groupInvitationsRecyclerView.setAdapter(groupInvitationsAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GroupInvitationsActivity.this, "Error in Json" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GroupInvitationsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userId",userid);
                return params;
            }
        };

        RequestHandler.getInstance(GroupInvitationsActivity.this).addToRequestQueue(stringRequest);

    }
}