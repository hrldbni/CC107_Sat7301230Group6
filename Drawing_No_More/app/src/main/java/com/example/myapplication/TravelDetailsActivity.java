package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TravelDetailsActivity extends AppCompatActivity {

    Dialog inviteFriendDialog;
    ImageView backBtn;
    Button inviteFriendsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        getSupportActionBar().hide();

        inviteFriendDialog = new Dialog(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inviteFriendsBtn = (Button) findViewById(R.id.inviteFriendsBtn);
        inviteFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent actnew = new Intent(TravelDetailsActivity.this, InviteFriendActivity.class);
                startActivity(actnew);
               // openInviteFriendDialog();


            }

            private void openInviteFriendDialog() {

                inviteFriendDialog.setContentView(R.layout.activity_invite_friend);
                inviteFriendDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                inviteFriendDialog.show();

            }
        });
    }
}