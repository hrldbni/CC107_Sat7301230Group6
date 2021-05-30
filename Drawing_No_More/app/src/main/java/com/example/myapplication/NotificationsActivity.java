package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView notificationRecyclerView;
    AdapterNotifications notificationsAdapter;
    List<ModelNotifications> modelNotificationsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        modelNotificationsList = new ArrayList<>();
        notificationRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));

        modelNotificationsList.add(new ModelNotifications(1,"This is first Notification","2","First Notification information is listed here","05/21/2021","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fnotification&psig=AOvVaw1Pq1y56xyL7AOdAxSgZcBA&ust=1622447474525000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKjD_fr18PACFQAAAAAdAAAAABAD"));
        modelNotificationsList.add(new ModelNotifications(1,"This is first Notification","2","First Notification information is listed here","05/21/2021","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fnotification&psig=AOvVaw1Pq1y56xyL7AOdAxSgZcBA&ust=1622447474525000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKjD_fr18PACFQAAAAAdAAAAABAD"));
        modelNotificationsList.add(new ModelNotifications(1,"This is first Notification","2","First Notification information is listed here","05/21/2021","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fnotification&psig=AOvVaw1Pq1y56xyL7AOdAxSgZcBA&ust=1622447474525000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKjD_fr18PACFQAAAAAdAAAAABAD"));


        notificationsAdapter = new AdapterNotifications(NotificationsActivity.this, modelNotificationsList);
        notificationRecyclerView.setAdapter(notificationsAdapter);



    }




}