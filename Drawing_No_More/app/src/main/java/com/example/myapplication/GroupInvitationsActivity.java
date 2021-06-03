package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class GroupInvitationsActivity extends AppCompatActivity {

    ImageButton backBtn;

    RecyclerView groupInvitationsRecyclerView;
    AdapterGroupInvitations groupInvitationsAdapter;
    List<ModelGroupInvitations> modelGroupInvitationsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_invitations);
        getSupportActionBar().hide();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modelGroupInvitationsList = new ArrayList<>();
        groupInvitationsRecyclerView = (RecyclerView) findViewById(R.id.groupInvitationRecyclerView);
        groupInvitationsRecyclerView.setHasFixedSize(true);
        groupInvitationsRecyclerView.setLayoutManager(new LinearLayoutManager(GroupInvitationsActivity.this));

        modelGroupInvitationsList.add(new ModelGroupInvitations(4,"87271995GC98","9","https://www.phbus.com/product_images/uploaded_images/tagaytay-covid19-update-open-tourists.jpg","98"));
        modelGroupInvitationsList.add(new ModelGroupInvitations(4,"87271995GC98","9","https://www.phbus.com/product_images/uploaded_images/tagaytay-covid19-update-open-tourists.jpg","98"));
        modelGroupInvitationsList.add(new ModelGroupInvitations(4,"87271995GC98","9","https://www.phbus.com/product_images/uploaded_images/tagaytay-covid19-update-open-tourists.jpg","98"));

        groupInvitationsAdapter = new AdapterGroupInvitations(GroupInvitationsActivity.this, modelGroupInvitationsList);
        groupInvitationsRecyclerView.setAdapter(groupInvitationsAdapter);




    }
}