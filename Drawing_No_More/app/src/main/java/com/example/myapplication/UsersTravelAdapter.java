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
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Hello", Toast.LENGTH_LONG).show();
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

        public TextView placeTitle, travelDate, currentFund,  availableFund;
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


        }
    }
}
