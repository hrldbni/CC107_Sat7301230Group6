package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterDestinationImages extends RecyclerView.Adapter<AdapterDestinationImages.AdapterDestinationImagesViewHolder>{

    public Context mCtx;
    public List<ModelDestinationImages> modelDestinationImagesList;

    public AdapterDestinationImages(Context mCtx, List<ModelDestinationImages> modelDestinationImagesList) {
        this.mCtx = mCtx;
        this.modelDestinationImagesList = modelDestinationImagesList;
    }

    @NonNull
    @Override
    public AdapterDestinationImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_destination_images, null);
        return new AdapterDestinationImages.AdapterDestinationImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDestinationImagesViewHolder holder, int position) {

        ModelDestinationImages modelDestinationImages = modelDestinationImagesList.get(position);

        Glide.with(mCtx)
                .load(modelDestinationImages.getDestinationImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(holder.destinationImage);

        holder.destinationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "This photo is provided by Unsplash.com", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelDestinationImagesList.size();
    }

    class AdapterDestinationImagesViewHolder extends RecyclerView.ViewHolder{

        public ImageView destinationImage;

        public AdapterDestinationImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            destinationImage = itemView.findViewById(R.id.destinationImage);

        }
    }

}
