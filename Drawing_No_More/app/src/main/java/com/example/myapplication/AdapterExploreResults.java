package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterExploreResults extends RecyclerView.Adapter<AdapterExploreResults.AdapterExploreResultsViewHolder>{

    List<ModelExploreResults> modelExploreResultsList;
    Context mCtx;

    public AdapterExploreResults(Context mCtx, List<ModelExploreResults> modelExploreResultsList) {
        this.mCtx = mCtx;
        this.modelExploreResultsList = modelExploreResultsList;

    }

    @NonNull
    @Override
    public AdapterExploreResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.explore_search_results, null);
        return new AdapterExploreResults.AdapterExploreResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExploreResultsViewHolder holder, int position) {
        ModelExploreResults modelExploreResults = modelExploreResultsList.get(position);

        Glide.with(mCtx)
                .load(modelExploreResults.getExploreResultsImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(holder.exploreResultImage);

        holder.exploreResultTitle.setText(modelExploreResults.getExploreResultsTitle());
        holder.exploreResultsLocation.setText(modelExploreResults.getExploreResultsLocation());
        holder.exploreResultsDescription.setText(modelExploreResults.getExploreResultsImage());
        holder.exploreResultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actnew = new Intent(mCtx, PreviewDestination.class);
                actnew.putExtra("place_title", modelExploreResults.getExploreResultsTitle());
                actnew.putExtra("place_location", modelExploreResults.getExploreResultsLocation());
                actnew.putExtra("place_img", modelExploreResults.getExploreResultsImage());
                mCtx.startActivity(actnew);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelExploreResultsList.size();
    }

    class AdapterExploreResultsViewHolder extends RecyclerView.ViewHolder{

        ImageView exploreResultImage;
        TextView exploreResultTitle, exploreResultsLocation, exploreResultsDescription;
        String id;

        public AdapterExploreResultsViewHolder(@NonNull View itemView) {
            super(itemView);

            exploreResultImage  = itemView.findViewById(R.id.exploreResultImage);
            exploreResultTitle = itemView.findViewById(R.id.exploreResultTitle);
            exploreResultsLocation = itemView.findViewById(R.id.exploreResultLocation);
            exploreResultsDescription = itemView.findViewById(R.id.exploreResultDescription);
        }
    }
}
