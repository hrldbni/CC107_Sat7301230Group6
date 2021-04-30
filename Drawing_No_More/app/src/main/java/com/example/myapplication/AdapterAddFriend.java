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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.AdapterAddFriendViewHolder> {

    private Context mCtx;
    List<ModelAddFriend> modelAddFriendList;
    String addFriendUid;

    public AdapterAddFriend(Context mCtx, List<ModelAddFriend> modelAddFriendList) {
        this.mCtx = mCtx;
        this.modelAddFriendList = modelAddFriendList;
    }

    @NonNull
    @Override
    public AdapterAddFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.searchfriends_list, null);
        return new AdapterAddFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddFriendViewHolder holder, int position) {
        ModelAddFriend modelAddFriend = modelAddFriendList.get(position);

        Glide.with(mCtx)
                .load(modelAddFriend.getAddFriendImage())
                .placeholder(R.drawable.loader)
                .into(holder.addFriendImage);

        holder.addFriendName.setText(modelAddFriend.getAddFriendName());
        if (modelAddFriend.getAddFriendCheck().equalsIgnoreCase("Yes")){
            holder.addFriendBtn.setBackgroundColor(R.drawable.drawingwhite);
            holder.addFriendBtn.setText("Friends");
            holder.addFriendBtn.setEnabled(false);
        } else if (modelAddFriend.getAddFriendCheck().equalsIgnoreCase("No")){
            holder.addFriendBtn.setText("Add Friend");
            holder.addFriendBtn.setEnabled(true);
            holder.addFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Start of Adding Friend

                            String userid = String.valueOf(SharedPrefManager.getUid()).toString().trim();
                            String request_to_userId = String.valueOf(modelAddFriend.getAddFriend_userId()).toString().trim();

                            StringRequest stringRequest = new StringRequest(
                                    Request.Method.POST,
                                    Constants.URL_ADDFRIEND,
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
                                    params.put("user_id_by", userid);
                                    params.put("user_id_to", request_to_userId);
                                    return params;
                                }
                            };

                            RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);



                    //End of addign Friend



                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelAddFriendList.size();
    }

    class AdapterAddFriendViewHolder extends RecyclerView.ViewHolder{

        ImageView addFriendImage;
        TextView addFriendName;
        Button addFriendBtn;

        public AdapterAddFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            addFriendImage = itemView.findViewById(R.id.addFriendImage);
            addFriendName = itemView.findViewById(R.id.addFriendName);
            addFriendBtn = itemView.findViewById(R.id.addFriendBtn);

        }
    }
}
