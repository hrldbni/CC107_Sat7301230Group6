package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceViewerActivity extends AppCompatActivity {

    ImageButton imgtravel;
    ImageButton imgprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);
        getSupportActionBar().hide();
        getIncomingIntent();
//button to travel activity
        imgtravel = (ImageButton) findViewById(R.id.btntravel);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(PlaceViewerActivity.this, MyTravelActivity.class);
                startActivity(actnew);
            }
        });
//button to profile
        imgprofile = (ImageButton) findViewById(R.id.btnprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(PlaceViewerActivity.this, ProfileActivity.class);
                startActivity(actnew);
            }


        });


    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("image") && getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("rating")){
            String image_url = getIntent().getStringExtra("image");
            String image_title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");

            TextView imageTitle = (TextView) findViewById(R.id.imageTitle);
            TextView imageLocation = (TextView) findViewById(R.id.imageLocation);
            ImageView imageViewer = (ImageView) findViewById(R.id.imageViewer);

            Glide.with(this)
                    .asBitmap()
                    .load(image_url)
                    .into(imageViewer);

            imageTitle.setText(image_title);
            imageLocation.setText(description);


        }
    }


}