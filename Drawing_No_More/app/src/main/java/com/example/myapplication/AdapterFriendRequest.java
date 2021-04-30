package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterFriendRequest extends RecyclerView.Adapter<AdapterFriendRequest.AdapterFriendsRequestViewHolder> {

    private Context mCtx;
    List<ModelFriendRequest> modelFriendRequestList;

    public AdapterFriendRequest(Context mCtx, List<ModelFriendRequest> modelFriendRequestList) {
        this.mCtx = mCtx;
        this.modelFriendRequestList = modelFriendRequestList;
    }

    @NonNull
    @Override
    public AdapterFriendsRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.request_list, null);

        return new AdapterFriendRequest.AdapterFriendsRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFriendsRequestViewHolder holder, int position) {
        ModelFriendRequest modelFriendRequest = modelFriendRequestList.get(position);

        Glide.with(mCtx)
                .load(modelFriendRequest.getRequestorImage())
                .placeholder(R.drawable.loader)
                .into(holder.requestorImage);

        holder.requestorName.setText(modelFriendRequest.getRequestorName());
        holder.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRequest();
            }

            private void confirmRequest() {
                //Start for Database Manipulation
                String request_id = String.valueOf(modelFriendRequest.getRequestId()).trim();
                String userid = String.valueOf(SharedPrefManager.getUid());
                String request_by_userId = String.valueOf(modelFriendRequest.getRequestorUserId()).trim();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.URL_ACCEPTFRIENDS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    Toast.makeText(mCtx, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mCtx, "Error in Json" + e, Toast.LENGTH_LONG).show();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("request_id", request_id);
                        params.put("user_id_by", request_by_userId);
                        params.put("user_id_to", userid);
                        return params;
                    }
                };

                RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);


                //End -->
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Clicked Remove for " + modelFriendRequest.getRequestorUserId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFriendRequestList.size();
    }


    class AdapterFriendsRequestViewHolder extends RecyclerView.ViewHolder{

        ImageView requestorImage;
        TextView requestorName;
        Button confirmButton, removeButton;
        int requestor_userId;
        int requestId;

        public AdapterFriendsRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            requestorImage = itemView.findViewById(R.id.requestorImage);
            requestorName = itemView.findViewById(R.id.requestPersonName);
            confirmButton = itemView.findViewById(R.id.confirmButton);
            removeButton = itemView.findViewById(R.id.removeRequest);

        }
    }
}
