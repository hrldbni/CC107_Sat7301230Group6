package com.example.myapplication;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterFriendsList extends RecyclerView.Adapter<AdapterFriendsList.AdapterFriendsListViewHolder> {

    private Context mCtx;
    private List<ModelFriends> modelFriendsList;

    public AdapterFriendsList(Context mCtx, List<ModelFriends> modelFriendsList) {
        this.mCtx = mCtx;
        this.modelFriendsList = modelFriendsList;
    }

    @NonNull
    @Override
    public AdapterFriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.friends_list, null);
        return new AdapterFriendsListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFriendsListViewHolder holder, int position) {
        ModelFriends modelFriends = modelFriendsList.get(position);

        Glide.with(mCtx)
                .load(modelFriends.getPersonImage())
                .placeholder(R.drawable.loader)
                .into(holder.personImage);
        holder.personName.setText(modelFriends.getPersonName());
        holder.unfriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start of friend Removal

                        String friend_user_id = String.valueOf(SharedPrefManager.getUid()).toString().trim();
                        String friend_user_id2 = String.valueOf(modelFriends.getPersonId()).toString().trim();

                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                Constants.URL_REMOVEFRIEND,
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
                                params.put("friend_user_id", friend_user_id);
                                params.put("friend_user_id2", friend_user_id2);
                                return params;
                            }
                        };

                        RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);


                //End of Friend Removal

            }
        });

        holder.inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Clicked invite", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFriendsList.size();
    }

    class AdapterFriendsListViewHolder extends RecyclerView.ViewHolder{

        ImageView personImage;
        TextView personName;
        Button unfriendBtn, inviteBtn;
        int personId;

        public AdapterFriendsListViewHolder(@NonNull View itemView) {
            super(itemView);

            personImage = itemView.findViewById(R.id.personImage);
            personName = itemView.findViewById(R.id.personName);
            unfriendBtn = itemView.findViewById(R.id.unfriendButton);
            inviteBtn = itemView.findViewById(R.id.inviteButton);


        }
    }
}
