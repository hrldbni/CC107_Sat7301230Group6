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

public class AdapterWhatsNew extends RecyclerView.Adapter<AdapterWhatsNew.AdapterWhatsNewViewHolder>{

    private Context mCtx;
    private List<ModelWhatsNew> modelWhatsNewList;

    public AdapterWhatsNew(Context mCtx, List<ModelWhatsNew> modelWhatsNewList) {
        this.mCtx = mCtx;
        this.modelWhatsNewList = modelWhatsNewList;
    }

    @NonNull
    @Override
    public AdapterWhatsNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.whats_new, null);
        return new AdapterWhatsNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWhatsNewViewHolder holder, int position) {
        ModelWhatsNew modelWhatsNew = modelWhatsNewList.get(position);


        Glide.with(mCtx)
                .load(modelWhatsNew.getImage())
                .placeholder(R.drawable.loader)
                .into(holder.whatsNewImage);

        holder.whatsNewTitle.setText(modelWhatsNew.getPlaceTitle());
        holder.whatsNewLocation.setText(modelWhatsNew.getPlaceLocation());
    }

    @Override
    public int getItemCount() {
        return modelWhatsNewList.size();
    }

    class AdapterWhatsNewViewHolder extends RecyclerView.ViewHolder {

        ImageView whatsNewImage;
        TextView whatsNewTitle;
        TextView whatsNewLocation;

        public AdapterWhatsNewViewHolder(@NonNull View itemView) {
             super(itemView);

             whatsNewImage = itemView.findViewById(R.id.whatsNewImage);
             whatsNewTitle = itemView.findViewById(R.id.whatsNewTitle);
             whatsNewLocation = itemView.findViewById(R.id.whatsNewLocation);

         }
     }

}
