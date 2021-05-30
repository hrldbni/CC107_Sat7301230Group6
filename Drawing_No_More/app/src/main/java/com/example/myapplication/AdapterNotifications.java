package com.example.myapplication;

import android.content.Context;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.AdapterNotificationsHolder> {

    private Context mCtx;
    private List<ModelNotifications> modelNotificationsList;

    public AdapterNotifications(Context mCtx, List<ModelNotifications> modelNotificationsList) {
        this.mCtx = mCtx;
        this.modelNotificationsList = modelNotificationsList;
    }

    @NonNull
    @Override
    public AdapterNotificationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_notifications, null);
        return new AdapterNotifications.AdapterNotificationsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotificationsHolder holder, int position) {
        ModelNotifications modelNotifications = modelNotificationsList.get(position);

        Glide.with(mCtx)
                .load(modelNotifications.getNotification_img())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(holder.notificationImage);

        holder.notificationTitle.setText(modelNotifications.getNotification_title());
        holder.notificationAbout.setText(modelNotifications.getNotification_about());
        holder.notificationDate.setText(modelNotifications.getNotification_date());

    }

    @Override
    public int getItemCount() {
        return modelNotificationsList.size();
    }

    class AdapterNotificationsHolder extends RecyclerView.ViewHolder{

        CircleImageView notificationImage;
        TextView notificationTitle;
        TextView notificationAbout;
        TextView notificationDate;


        public AdapterNotificationsHolder(@NonNull View itemView) {
            super(itemView);

            notificationImage = itemView.findViewById(R.id.notificationImg);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationAbout = itemView.findViewById(R.id.notificationsAbout);
            notificationDate = itemView.findViewById(R.id.notificationDate);

        }
    }

}
