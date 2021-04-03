package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTravel extends AppCompatActivity {


    RecyclerView recyclerView;
    PlacesAdapter adapter;
    private ProgressDialog progressDialog;
    List<Places> placesList;
    Button searchBtn;
    Button textViewPrice;
    ImageButton imgtravel;
    ImageButton imgprofile;
    EditText searchText;
    Button drawingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_search);
        getSupportActionBar().hide();

//button to travel activity
        imgtravel = (ImageButton) findViewById(R.id.btntravel);
        imgtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(SearchTravel.this, MyTravelActivity.class);
                startActivity(actnew);
            }
        });
//button to profile
        imgprofile = (ImageButton) findViewById(R.id.btnprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actnew = new Intent(SearchTravel.this, ProfileActivity.class);
                startActivity(actnew);
            }
        });


        placesList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching for best places, Please Wait...");

        searchText = (EditText) findViewById(R.id.searchText);

        loadPlaces();


        searchBtn = (Button) findViewById(R.id.searchPlace);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchPlace();
            }
        });


    }

    private void searchPlace(){

        final String tags = searchText.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                              placesList.clear();
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int placeid = placesObject.getInt("placeid");
                                String placetitle = placesObject.getString("placetitle");
                                String location = placesObject.getString("location");
                                double placerating = placesObject.getDouble("placerating");
                                String placeimage = placesObject.getString("placeimage");
                              //  String about = placesObject.getString("placeimage");

                                placesList.add(
                                        new Places(
                                                placeid,placetitle, location, placerating, "View Details", placeimage));

                            }

                            adapter = new PlacesAdapter(SearchTravel.this, placesList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Hello "+ e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }




    private void loadPlaces() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_PLACES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int placeid = placesObject.getInt("placeid");
                                String placetitle = placesObject.getString("placetitle");
                                String location = placesObject.getString("location");
                                double placerating = placesObject.getDouble("placerating");
                                String placeimage = placesObject.getString("placeimage");

                              //  String about = placesObject.getString("about");


                                placesList.add(
                                        new Places(
                                                placeid,placetitle, location, placerating, "View Details", placeimage));

                            }

                            adapter = new PlacesAdapter(SearchTravel.this, placesList);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SearchTravel.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

       Volley.newRequestQueue(this).add(stringRequest);


    }
}