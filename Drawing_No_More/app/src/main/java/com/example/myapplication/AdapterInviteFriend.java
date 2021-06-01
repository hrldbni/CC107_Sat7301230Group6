package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static java.sql.Types.NULL;

public class AdapterInviteFriend extends RecyclerView.Adapter<AdapterInviteFriend.AdapterInviteFriendViewHolder> {

    public String travelId = "";
    public String groupTravelCode = "";
    Context mCtx;
    List<ModelInviteFriend> modelInviteFriendList;


    public AdapterInviteFriend(Context mCtx, List<ModelInviteFriend> modelInviteFriendList) {
        this.mCtx = mCtx;
        this.modelInviteFriendList = modelInviteFriendList;
    }

    @NonNull
    @Override
    public AdapterInviteFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.invite_friend_layout, null);
        return new AdapterInviteFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInviteFriendViewHolder holder, int position) {

        ModelInviteFriend modelInviteFriend = modelInviteFriendList.get(position);

        Glide.with(mCtx)
                .load(modelInviteFriend.getInviteFriendImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(holder.inviteFriendImage);

        holder.inviteFriendName.setText(modelInviteFriend.getInviteFriendName());
        holder.inviteFriendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentTravelIdFinal = "";

                SharedPreferences mSharedPreferences = mCtx.getSharedPreferences("TravelId", MODE_PRIVATE);
                int currentTravelId = mSharedPreferences.getInt("travelId", NULL);

                currentTravelIdFinal = String.valueOf(currentTravelId);

                //THis is for clearing the Shared Preferences after being used for other transaction to Work

                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.clear();
                mEditor.apply();

                String travelId = currentTravelIdFinal;

                //getGroupTravel Code

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.URL_GETGROUPTRAVELCODE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    groupTravelCode = jsonObject.getString("Group Travel Code");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mCtx, "Error in Json " + e, Toast.LENGTH_LONG).show();
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
                        params.put("travelId", travelId);
                        return params;
                    }
                };

                RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);



            }
        });
    }


    @Override
    public int getItemCount() {
        return modelInviteFriendList.size();
    }

    class AdapterInviteFriendViewHolder extends RecyclerView.ViewHolder{

        ImageView inviteFriendImage;
        TextView inviteFriendName;
        Button inviteFriendNow;

        public AdapterInviteFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            inviteFriendImage = itemView.findViewById(R.id.inviteFriendImage);
            inviteFriendName = itemView.findViewById(R.id.inviteFriendName);
            inviteFriendNow = itemView.findViewById(R.id.inviteFriendNow);
        }
    }
}
