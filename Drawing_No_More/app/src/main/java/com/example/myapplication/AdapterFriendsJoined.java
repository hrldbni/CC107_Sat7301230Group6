package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFriendsJoined extends RecyclerView.Adapter<AdapterFriendsJoined.AdapterFriendsJoinedViewHolder>{

    private Context mCtx;
    List<ModelFriendsJoined> modelFriendsJoinedList;

    public AdapterFriendsJoined(Context mCtx, List<ModelFriendsJoined> modelFriendsJoinedList) {
        this.mCtx = mCtx;
        this.modelFriendsJoinedList = modelFriendsJoinedList;
    }

    @NonNull
    @Override
    public AdapterFriendsJoinedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.list_friends_joined, null);
        return new AdapterFriendsJoined.AdapterFriendsJoinedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFriendsJoinedViewHolder holder, int position) {
        ModelFriendsJoined modelFriendsJoined = modelFriendsJoinedList.get(position);

        Glide.with(mCtx)
                .load(modelFriendsJoined.friendProfileImage)
                .placeholder(R.drawable.loader)
                .into(holder.friendProfile);
        holder.friendName.setText(modelFriendsJoined.getFriendName());
        holder.removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mCtx, "Remove Friend", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFriendsJoinedList.size();
    }

    class AdapterFriendsJoinedViewHolder extends RecyclerView.ViewHolder{

        CircleImageView friendProfile;
        TextView friendName;
        Button removeFriend;

        public AdapterFriendsJoinedViewHolder(@NonNull View itemView) {
            super(itemView);

            friendProfile = itemView.findViewById(R.id.friendProfile);
            friendName = itemView.findViewById(R.id.friendName);
            removeFriend = itemView.findViewById(R.id.removeFriendBtn);

        }
    }
}
