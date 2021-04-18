package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return modelFriendsList.size();
    }

    class AdapterFriendsListViewHolder extends RecyclerView.ViewHolder{

        ImageView personImage;
        TextView personName;
        int personId;

        public AdapterFriendsListViewHolder(@NonNull View itemView) {
            super(itemView);

            personImage = itemView.findViewById(R.id.personImage);
            personName = itemView.findViewById(R.id.personName);

        }
    }
}
