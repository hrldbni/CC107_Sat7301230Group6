package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindPlace extends AppCompatActivity {

    private static final String TAG = "FindPlace";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    String searchText;
    ImageView placeImage;
    FloatingActionButton addToTravel;
    TextView placeDescription, placeRating, placeTitle, placeLocation;
    EditText findPlace;
    String placeTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);
        getSupportActionBar().hide();

        placeImage = (ImageView) findViewById(R.id.placeImage);
        findPlace = (EditText) findViewById(R.id.findPlace);
        placeDescription = (TextView) findViewById(R.id.placeDescription);
        placeRating = (TextView) findViewById(R.id.placeRating);
        placeTitle = (TextView) findViewById(R.id.placeTitle);
        addToTravel = (FloatingActionButton) findViewById(R.id.addToTravel);
        placeLocation = (TextView) findViewById(R.id.placeLocation);

        Places.initialize(getApplicationContext(), "AIzaSyDV2EkJozpBePZ-ugdvzCfyLYMe72-BRDk");

        if (isServicesOk()){

            findPlace.setFocusable(false);
            findPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.PHOTO_METADATAS, Place.Field.RATING, Place.Field.TYPES);

                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(FindPlace.this);
                    startActivityForResult(intent, 100);

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Error: there is a problem on your Google Services", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){

            Place place = Autocomplete.getPlaceFromIntent(data);

            findPlace.setText(place.getAddress());

            String placeDesc = String.valueOf(place.getTypes());
            placeDesc = placeDesc.replaceAll("\\[","");

            String placeDesc2 = placeDesc;
            placeDesc2 = placeDesc2.replaceAll("\\]","");

            String finalDesc = placeDesc2;
            finalDesc = finalDesc.replaceAll("_", " ");



            if (String.valueOf(place.getRating()) == "null"){
                placeRating.setText("No Ratings Yet");
                placeLocation.setText(String.valueOf(place.getAddress()));
                placeDescription.setText(finalDesc);
                placeTitle.setText(String.valueOf(place.getName()));
                placeTags = place.getName();
            } else {
                placeRating.setText(String.valueOf(place.getRating()));
                placeLocation.setText(String.valueOf(place.getAddress()));
                placeDescription.setText(finalDesc);
                placeTitle.setText(String.valueOf(place.getName()));
                placeTags = place.getName();

            }

             addPhoto();

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addPhoto() {

        String tags = placeTags;
        String finalWord = tags.replaceAll(" ", "+");

        //Constants.URL_GETPLACEIMAGE+finalWord+Constants.URL_GETPLACEIMAGE2


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_GETPLACEIMAGE + finalWord+ Constants.URL_GETPLACEIMAGE2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String imageUrl = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < 2; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                       imageUrl = jsonObject.getString("largeImageURL");
                    }

                  if (imageUrl.isEmpty()){
                      Glide.with(getApplicationContext())
                              .load("https://i.pinimg.com/originals/1f/aa/3c/1faa3cda455c865f037d63a223577ab5.png")
                              .into(placeImage);
                  } else {
                      Glide.with(getApplicationContext())
                              .load(imageUrl)
                              .into(placeImage);
                  }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindPlace.this, "An Error Occured while Executing Volley " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }



    public boolean isServicesOk(){
        Log.d(TAG, "isServiceOk: checking google services version ");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(FindPlace.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOk: Google play services is working ");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOk: An error occured but we can fix it");

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(FindPlace.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else
        {
            Toast.makeText(this, "You can't make Maps Request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}