package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hottravel_item, container, false);

        ImageView placeImage;
        TextView placeTitle, placeLocation;

        placeImage = view.findViewById(R.id.image);
        placeTitle = view.findViewById(R.id.imageTitle);
        placeLocation =  view.findViewById(R.id.imageLocation);

        Glide.with(context)
                .load(models.get(position).getImage())
                .placeholder(R.drawable.loader)
                .into(placeImage);

        placeTitle.setText(models.get(position).getPlaceTitle());
        placeLocation.setText(models.get(position).getPlaceLocation());

        placeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actnew = new Intent(context, PreviewDestination.class);
                actnew.putExtra("place_title", models.get(position).getPlaceTitle());
                actnew.putExtra("place_location", models.get(position).getPlaceLocation());
                actnew.putExtra("place_img", models.get(position).getImage());
                context.startActivity(actnew);


            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
