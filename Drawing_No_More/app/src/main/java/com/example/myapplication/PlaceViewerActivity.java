package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlaceViewerActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();


    public int placeIdText;
    ImageButton imgtravel;
    ImageButton imgprofile;
    ImageView backBtn;
    EditText travelDate, travelFund;
    TextView placeId;
    TextView userid, travelDestination, imageAbout;
    private ProgressDialog progressDialog;

    Button insertBtn;
    String place;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);
        travelDate = findViewById(R.id.travelDate);
        getSupportActionBar().hide();
        getIncomingIntent();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageAbout = (TextView) findViewById(R.id.imageAbout);
        travelDate = (EditText) findViewById(R.id.travelDate);
        DatePickerDialog.OnDateSetListener date = this::onDateSet;

        travelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlaceViewerActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        travelFund  = (EditText) findViewById(R.id.travelFund);

        insertBtn = (Button) findViewById(R.id.insertBtn);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (travelDate.getText().toString().trim().isEmpty()){
                  AlertDialog alertDialog1 = new AlertDialog.Builder(
                          PlaceViewerActivity.this).create();


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
                          PlaceViewerActivity.this).create();


                  alertDialog1.setTitle("Hey");
                  alertDialog1.setMessage("You cannot Travel without a Budget!");
                  alertDialog1.setIcon(R.drawable.money);
                  alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });

                  alertDialog1.show();


              } else {

                  String date = travelDate.getText().toString().trim();

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

        addAboutPlace();

    }

    private void addAboutPlace() {

        final String placeid = String.valueOf(placeIdText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDABOUTPLACE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();

                            JSONObject obj = new JSONObject(response);
                            imageAbout.setText(obj.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() + "Error", Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("placeid", placeid);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void addTravel() {

        final String userTravelId = String.valueOf(SharedPrefManager.getInstance(this).getUid());
        final String travelDateText = travelDate.getText().toString().trim();
        final String travelFundText = travelFund.getText().toString().trim();
        final String travelDestinationText = place;


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDTRAVEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);


                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    PlaceViewerActivity.this).create();


                            alertDialog1.setTitle("Hey");
                            alertDialog1.setMessage(obj.getString("message"));
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Intent newIntent;
                                    newIntent = new Intent(PlaceViewerActivity.this, SearchTravel.class);
                                    startActivity(newIntent);
                                    finish();
                                }
                            });

                            alertDialog1.show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() + "Error", Toast.LENGTH_LONG).show();
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
                params.put("placeid", String.valueOf(placeIdText));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    private void getIncomingIntent(){
        if (getIntent().hasExtra("image") && getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("rating")){

            String image_url = getIntent().getStringExtra("image");
            String image_title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String image_id = getIntent().getStringExtra("idPlace");
           // String abouts = getIntent().getStringExtra("abouts");


            TextView imageTitle = (TextView) findViewById(R.id.imageTitle);
            TextView imageLocation = (TextView) findViewById(R.id.imageLocation);
            ImageView imageViewer = (ImageView) findViewById(R.id.imageViewer);
        //    TextView imageabout = (TextView) findViewById(R.id.imageabout);


            Glide.with(this)
                    .asBitmap()
                    .load(image_url)
                    .into(imageViewer);

            imageTitle.setText(image_title);
            imageLocation.setText(description);

            place = image_title;
            placeIdText = Integer.parseInt(image_id);





        }
    }
    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        travelDate.setText(sdf.format(myCalendar.getTime()));


    }


}