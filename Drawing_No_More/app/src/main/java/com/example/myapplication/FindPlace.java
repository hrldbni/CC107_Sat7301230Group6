package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class FindPlace extends AppCompatActivity {

    private static final String TAG = "FindPlace";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    String currentImageUrl;
    private ProgressDialog progressDialog;

    String searchText;
    String placeLocation;
    ImageView backBtn;
    TextView totalResults;
    EditText findPlace;
    String placeTags;

    RecyclerView exploreResultsRescyclerView;
    AdapterExploreResults exploreResultsAdapter;
    List<ModelExploreResults> modelExploreResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait for a moment...");

        findPlace = (EditText) findViewById(R.id.findPlace);
        totalResults = (TextView) findViewById(R.id.totalResults);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        modelExploreResultsList = new ArrayList<>();

        exploreResultsRescyclerView = (RecyclerView) findViewById(R.id.searchPlaceResultsRecyclerView);
        exploreResultsRescyclerView.setHasFixedSize(true);
        exploreResultsRescyclerView.setLayoutManager(new LinearLayoutManager(this));

//        modelExploreResultsList.add(new ModelExploreResults("","https://lh3.googleusercontent.com/proxy/VCw7phXxiZ2HDbTnrjHx3vNhnu12IuMQBM91h4HVizqr-Y1dgnd8HSPXJKQljLz6f4cLGz1bGzVXQ6OARqBQywzp80Vb7xTbPSoK7zJbCdO9S50CzEz0r6mMOozfTYxDbw","Baguiu","Bagui bagui000","Sa bagyio"));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            findPlace.setText(place.getName());
            searchText= place.getName();
            placeLocation = place.getAddress();
            placeTags = place.getName();


            exploreResults();

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void exploreResults() {

        progressDialog.show();
        String tags = placeTags;
        String finalWord = tags.replaceAll(" ", "+");

        //

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.URL_GETPLACEIMAGE+finalWord+Constants.URL_GETPLACEIMAGE2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                modelExploreResultsList.clear();
                String description = "";
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    totalResults.setText("Total Result(s) : "+String.valueOf(jsonArray.length()));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.getString("description").isEmpty() || jsonObject.getString("description") == "null"){
                           description = "Description : " + jsonObject.getString("alt_description");
                        } else {
                            description = "Description : " + jsonObject.getString("description");
                        }
                        JSONObject jsonObjectImages = jsonObject.getJSONObject("urls");
                        currentImageUrl = jsonObjectImages.getString("regular");


                        modelExploreResultsList.add(new ModelExploreResults("", currentImageUrl, searchText, placeLocation, description));

                    }

                    exploreResultsAdapter = new AdapterExploreResults(FindPlace.this, modelExploreResultsList);
                    exploreResultsRescyclerView.setAdapter(exploreResultsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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