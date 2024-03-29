package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.security.PublicKey;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>{

    private Context mCtx;
    private List<Places> placesList;
    int placeId;

    public PlacesAdapter(Context mCtx, List<Places> placesList) {
        this.mCtx = mCtx;
        this.placesList = placesList;
    }


    @Override
    public PlacesViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

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
        holder.textViewDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+places.getShortdesc());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mCtx.startActivity(mapIntent);
            }
        });
        holder.textViewRating.setText(String.valueOf(places.getRating()));
        //Button is not needed

        Glide.with(mCtx)
                .load(places.getImage())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+places.getShortdesc());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mCtx.startActivity(mapIntent);
            }
        });

        holder.textViewDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, PlaceViewerActivity.class);
                intent.putExtra("image", placesList.get(position).getImage());
                intent.putExtra("title", placesList.get(position).getTitle());
                intent.putExtra("description", placesList.get(position).getShortdesc());
                intent.putExtra("rating", placesList.get(position).getRating());
                int idPlace =placesList.get(position).getId();

                intent.putExtra("idPlace", String.valueOf(idPlace));

                mCtx.startActivity(intent);


            }
        });
        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, PlaceViewerActivity.class);
                intent.putExtra("image", placesList.get(position).getImage());
                intent.putExtra("title", placesList.get(position).getTitle());
                intent.putExtra("description", placesList.get(position).getShortdesc());
                intent.putExtra("rating", placesList.get(position).getRating());
                int idPlace =placesList.get(position).getId();

                intent.putExtra("idPlace", String.valueOf(idPlace));

                mCtx.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textViewTitle, textViewDesc, textViewRating;
        public Button textViewDraw;


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
