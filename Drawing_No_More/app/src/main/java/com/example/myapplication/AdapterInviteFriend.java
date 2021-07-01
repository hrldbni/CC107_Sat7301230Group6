package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

                Toast.makeText(mCtx, ""+travelId, Toast.LENGTH_SHORT).show();


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
