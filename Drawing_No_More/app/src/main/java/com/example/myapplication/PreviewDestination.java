package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PreviewDestination extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private ImageView backBtn;
    private ProgressDialog progressDialog;
    RecyclerView destinationImageRecyclerView;
    AdapterDestinationImages destinationImagesAdapter;
    List<ModelDestinationImages> modelDestinationImagesList;
    EditText travelFund;
    Button inviteFriendsBtn;

    String getDestinationImage, getDestinationLocation, getDestinationRating;
    ImageView destinationImage;
    TextView destinationTitle, destinationDescription, totalPhotos, dateSchedule;

    FloatingActionButton confirmTravel;
    String travelTypeText, placeDescription;

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

        //Date Selector
        DatePickerDialog.OnDateSetListener date = this::onDateSet;
        dateSchedule = (TextView) findViewById(R.id.text3);
        dateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PreviewDestination.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Confirm Travel
        travelFund = (EditText) findViewById(R.id.travelFund);
        confirmTravel = (FloatingActionButton) findViewById(R.id.confirmTravel);
        confirmTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateSchedule.getText().toString().trim().isEmpty() || dateSchedule.getText().toString().trim().equalsIgnoreCase("Date Travel Not Set")){
                    AlertDialog alertDialog1 = new AlertDialog.Builder(
                            PreviewDestination.this).create();


                    alertDialog1.setTitle("Hey");
                    alertDialog1.setMessage("You cannot Travel without a Date!");
                    alertDialog1.setIcon(R.drawable.pincillukisyon);
                    alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog1.show();
                } else if (travelFund.getText().toString().trim().isEmpty()){
                    AlertDialog alertDialog1 = new AlertDialog.Builder(
                            PreviewDestination.this).create();


                    alertDialog1.setTitle("Hey");
                    alertDialog1.setMessage("You cannot Travel without a Budget!");
                    alertDialog1.setIcon(R.drawable.money);
                    alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog1.show();


                } else {

                    String date = dateSchedule.getText().toString().trim();

                    try {
                        if (new SimpleDateFormat("MM/dd/yyyy").parse(date).before(new Date())) {
                            Toast.makeText(getApplicationContext(), "Please select a valid date for you to Prepare", Toast.LENGTH_SHORT).show();
                        } else {
                            addTravel();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                }


            }
        });


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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                travelTypeText = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modelDestinationImagesList = new ArrayList<>();
        destinationImageRecyclerView = (RecyclerView) findViewById(R.id.destinationImages);
        destinationImageRecyclerView.setHasFixedSize(true);
        destinationImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        getDestinationPhotos();

    }

    private void addTravel() {

            final String userTravelId = String.valueOf(SharedPrefManager.getInstance(PreviewDestination.this).getUid());
            final String travelDateText = dateSchedule.getText().toString().trim();
            final String travelFundText = travelFund.getText().toString().trim();
            final String travelDestinationText = destinationTitle.getText().toString().trim();
            final String travelType = travelTypeText.trim();
            final String location = destinationDescription.getText().toString().trim();
            final String placeImage = getIntent().getStringExtra("place_img").trim();
            final String placeDesciption = "no Value";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDTRAVEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);


                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    PreviewDestination.this).create();

                            String message = obj.getString("message");

                            if (message.equalsIgnoreCase("You already setup a travel for this date")){
                                    alertDialog1.setTitle("Hey");
                                    alertDialog1.setMessage("You already setup a travel for this date.");
                                    alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(PreviewDestination.this, "Please select a different date", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    alertDialog1.show();

                                  } else {
                                alertDialog1.setTitle("Hey");
                                alertDialog1.setMessage("Your travel to " +destinationTitle.getText()+" has now begin.");
                                alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent newIntent;
                                        newIntent = new Intent(PreviewDestination.this, HomeActivity.class);
                                        startActivity(newIntent);
                                        finish();
                                    }
                                });

                                alertDialog1.show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() + " Error", Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userTravelId", userTravelId);
                params.put("travelDate", travelDateText);
                params.put("travelFund", travelFundText);
                params.put("travelDestination", travelDestinationText);
                params.put("travel_type", travelType);
                params.put("location", location);
                params.put("placeimage", placeImage);
                params.put("placedescription", placeDesciption);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
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

    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateSchedule.setText(sdf.format(myCalendar.getTime()));

    }

}