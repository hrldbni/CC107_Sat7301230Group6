package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PreviewDestination extends AppCompatActivity {

    private ImageView backBtn;
    private ProgressDialog progressDialog;
    RecyclerView destinationImageRecyclerView;
    AdapterDestinationImages destinationImagesAdapter;
    List<ModelDestinationImages> modelDestinationImagesList;

    String getDestinationImage, getDestinationLocation, getDestinationRating;
    ImageView destinationImage;
    TextView destinationTitle, destinationDescription, totalPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_destination);
        getSupportActionBar().hide();

        destinationTitle = findViewById(R.id.destinationTitle);
        destinationDescription = findViewById(R.id.destinationDescription);
        destinationImage = findViewById(R.id.destinationImage);
        totalPhotos = (TextView) findViewById(R.id.totalPhotos);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait for a moment...");


        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("place_img"))
                .placeholder(R.drawable.loader)
                .into(destinationImage);

        destinationTitle.setText(getIntent().getStringExtra("place_title"));
        destinationDescription.setText(getIntent().getStringExtra("place_location"));
        getDestinationLocation = getIntent().getStringExtra("place_location");


        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.typeOfTravel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        modelDestinationImagesList = new ArrayList<>();
        destinationImageRecyclerView = (RecyclerView) findViewById(R.id.destinationImages);
        destinationImageRecyclerView.setHasFixedSize(true);
        destinationImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        getDestinationPhotos();

    }

    private void getDestinationPhotos() {
        progressDialog.show();
        String tags = destinationTitle.getText().toString().trim();
        String finalWord = tags.replaceAll(" ", "+");

        //

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.URL_GETPLACEIMAGE+finalWord+Constants.URL_GETPLACEIMAGE2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                modelDestinationImagesList.clear();
                String description = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    totalPhotos.setText("Photo(s) : "+String.valueOf(jsonArray.length()));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.getString("description").isEmpty() || jsonObject.getString("description") == "null"){
                            description = "Description : " + jsonObject.getString("alt_description");
                        } else {
                            description = "Description : " + jsonObject.getString("description");
                        }
                        JSONObject jsonObjectImages = jsonObject.getJSONObject("urls");
                        String currentImageUrl = jsonObjectImages.getString("regular");

                        modelDestinationImagesList.add(new ModelDestinationImages(1,"","",currentImageUrl));

                    }

                    destinationImagesAdapter = new AdapterDestinationImages(PreviewDestination.this,  modelDestinationImagesList);
                    destinationImageRecyclerView.setAdapter(destinationImagesAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PreviewDestination.this, "An Error Occured while Executing Volley " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}