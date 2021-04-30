package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterInviteFriend extends RecyclerView.Adapter<AdapterInviteFriend.AdapterInviteFriendViewHolder> {

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
                Toast.makeText(mCtx, "Clicked Name is : " + modelInviteFriend.getInviteFriendName(), Toast.LENGTH_SHORT).show();
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
