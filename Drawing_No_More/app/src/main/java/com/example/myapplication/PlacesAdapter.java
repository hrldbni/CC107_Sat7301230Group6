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

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>{

    private Context mCtx;
    private List<Places> placesList;

    public PlacesAdapter(Context mCtx, List<Places> placesList) {
        this.mCtx = mCtx;
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);

        PlacesViewHolder holder = new PlacesViewHolder(view);
        return new PlacesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Places places = placesList.get(position);
        holder.textViewTitle.setText(places.getTitle());
        holder.textViewDesc.setText(places.getShortdesc());
        holder.textViewRating.setText(String.valueOf(places.getRating()));
        //Button is not needed
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(places.getImage()));

    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating;
        Button textViewDraw;


        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewDraw = itemView.findViewById(R.id.textViewPrice);

        }
    }


}
