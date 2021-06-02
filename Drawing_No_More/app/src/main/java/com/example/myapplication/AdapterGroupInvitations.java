package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterGroupInvitations extends RecyclerView.Adapter<AdapterGroupInvitations.AdapterGroupInvitationsViewHolder>{

    private Context mCtx;
    private List<ModelGroupInvitations> modelGroupInvitationsList;

    public AdapterGroupInvitations(Context mCtx, List<ModelGroupInvitations> modelGroupInvitationsList) {
        this.mCtx = mCtx;
        this.modelGroupInvitationsList = modelGroupInvitationsList;
    }

    @NonNull
    @Override
    public AdapterGroupInvitationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_group_travel_invitations, null);
        return new AdapterGroupInvitations.AdapterGroupInvitationsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGroupInvitationsViewHolder holder, int position) {
        ModelGroupInvitations modelGroupInvitations = modelGroupInvitationsList.get(position);


        Glide.with(mCtx)
                .load("https://image.flaticon.com/icons/png/512/1647/1647547.png")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(holder.placeImage);

        //holder.placeTitle.setText(modelGroupInvitations.get);




    }

    @Override
    public int getItemCount() {
        return modelGroupInvitationsList.size();
    }

    class AdapterGroupInvitationsViewHolder extends RecyclerView.ViewHolder{


        ImageView placeImage;
        TextView placeTitle;
        Button acceptTravelBtn, declineTravelBtn;

        public AdapterGroupInvitationsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.placeImage);
            placeTitle = itemView.findViewById(R.id.placeTitle);
            acceptTravelBtn = itemView.findViewById(R.id.acceptTravel);
            declineTravelBtn = itemView.findViewById(R.id.declineInvitation);
        }
    }


}
