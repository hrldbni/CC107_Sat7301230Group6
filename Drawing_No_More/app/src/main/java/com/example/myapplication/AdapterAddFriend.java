package com.example.myapplication;

import android.content.Context;
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

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.AdapterAddFriendViewHolder> {

    private Context mCtx;
    List<ModelAddFriend> modelAddFriendList;

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
                    Toast.makeText(mCtx, "Cliked add friend", Toast.LENGTH_SHORT).show();
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
