package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

public class UsersTravelAdapter extends RecyclerView.Adapter<UsersTravelAdapter.UsersTravelViewHolder> {

    private Context mCtx;
    private List<UserTravels> userTravelsList;

    public UsersTravelAdapter(Context mCtx, List<UserTravels> userTravelsList) {
        this.mCtx = mCtx;
        this.userTravelsList = userTravelsList;
    }

    @NonNull
    @Override
    public UsersTravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.usertravel_list_layout, null);

        UsersTravelAdapter.UsersTravelViewHolder holder = new UsersTravelAdapter.UsersTravelViewHolder(view);
        return new UsersTravelAdapter.UsersTravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersTravelViewHolder holder, int position) {

        UserTravels userTravel = userTravelsList.get(position);

        holder.placeTitle.setText(userTravel.getPlaceTitle());
        holder.travelDate.setText(userTravel.getTravelDate());
        holder.currentFund.setText(userTravel.getCurrentFund());
        holder.availableFund.setText(userTravel.getAvailableFund());

        if(userTravel.getTravelStatus().equalsIgnoreCase("Ready to Go")){
            holder.travelStatus.setText(userTravel.getTravelStatus());
            holder.travelStatus.setBackgroundColor(Color.parseColor("#58E0BF"));
        } else if (userTravel.getTravelStatus().equalsIgnoreCase("Pending")){
            holder.travelStatus.setText(userTravel.getTravelStatus());
            holder.travelStatus.setBackgroundColor(Color.MAGENTA);
            //Yellow
        } else {
            holder.travelStatus.setText(userTravel.getTravelStatus());
            holder.travelStatus.setBackgroundColor(Color.RED);
            //Red
        }


        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id =  userTravel.getId();
                Intent intent = new Intent(mCtx, ConfirmTravel.class);
                intent.putExtra("travelId", String.valueOf(id));
                intent.putExtra("eventLocation", userTravel.getTravelLocation());
                intent.putExtra("eventTitle", userTravel.getPlaceTitle());
                intent.putExtra("eventDate", userTravel.getTravelDate());
                intent.putExtra("travelStatus", userTravel.getTravelStatus());
                intent.putExtra("currentFund", userTravel.getCurrentFund());
                intent.putExtra("travelFund", userTravel.getAvailableFund());
                intent.putExtra("image", userTravel.getPlaceImage());

                mCtx.startActivity(intent);
                ((Activity) mCtx).finish();


            }
        });

        Glide.with(mCtx)
                .load(userTravel.getPlaceImage())
                .into(holder.placeImage );

    }

    @Override
    public int getItemCount() {
       return userTravelsList.size();
    }

    class UsersTravelViewHolder extends RecyclerView.ViewHolder {

        public TextView placeTitle, travelDate, currentFund,  availableFund,travelStatus;
        public ImageView placeImage;
        public Button viewDetails;



        public UsersTravelViewHolder(@NonNull View itemView) {
            super(itemView);

            placeTitle = itemView.findViewById(R.id.textViewPlace);
            travelDate = itemView.findViewById(R.id.travelDate);
            currentFund = itemView.findViewById(R.id.currentFund);
            availableFund = itemView.findViewById(R.id.availableFund);
            placeImage = itemView.findViewById(R.id.imageView);
            viewDetails = itemView.findViewById(R.id.viewDetails);
            travelStatus = itemView.findViewById(R.id.travelStatus);



        }
    }
}
