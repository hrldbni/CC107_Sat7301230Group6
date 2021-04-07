package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfirmTravel extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    TextView date, travelFundText, currentFundText, travelStatusText;
    TextView title;
    TextView location;
    String description;
    Button addEvent, confirmTravel;
    ImageView backBtn;
    ImageView imagePlace;
    String inputFund;
    int yourFund;
    int exactFund;
    String finalTravelStatus;
    String dateText;
    Button checkMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().hide();


           String travelId =  getIntent().getStringExtra("travelId");
           String placeTitle = getIntent().getStringExtra("eventTitle");
           String placeLocation =  getIntent().getStringExtra("eventLocation");
           String eventDate =  getIntent().getStringExtra("eventDate");
           String travelStatus =  getIntent().getStringExtra("travelStatus");
           String travelFund =  getIntent().getStringExtra("travelFund");
           String currentFund =  getIntent().getStringExtra("currentFund");
           String placeImage =  getIntent().getStringExtra("image");

            imagePlace = (ImageView) findViewById(R.id.imagePlace);
            imagePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+getIntent().getStringExtra("eventLocation"));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

        Glide.with(getApplicationContext())
                .load(placeImage)
                .into(imagePlace);

        description = "This is your travel to " + getIntent().getStringExtra("eventTitle") + ".";
        location = findViewById(R.id.placeLocation);
        location.setText(placeLocation);

        title = findViewById(R.id.placeTitle);
        title.setText(placeTitle);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent;
                newIntent = new Intent(ConfirmTravel.this, MyTravelActivity.class);
                startActivity(newIntent);
                finish();

            }
        });

        date = findViewById(R.id.travelDate);
        date.setText(eventDate);



        travelStatusText = findViewById(R.id.travelStatus);
        travelStatusText.setText(travelStatus);

        currentFundText = findViewById(R.id.currentFund);
        currentFundText.setText(currentFund);

        currentFundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmTravel.this);
                builder.setIcon(R.drawable.money);
                builder.setTitle("Add Fund?");
                builder.setMessage("How much fund would you like to add for your travel to " + placeTitle + "?");
                final EditText input = new EditText(ConfirmTravel.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            inputFund = input.getText().toString();
                                    if (input.getText().toString().isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "No Fund Added", Toast.LENGTH_SHORT).show();
                                    } else {
                                    yourFund = Integer.parseInt(currentFundText.getText().toString()) + Integer.parseInt(inputFund);
                                    exactFund = Integer.parseInt(travelFundText.getText().toString());

                                    currentFundText.setText(String.valueOf(yourFund));

                                    if (yourFund < exactFund) {
                                        finalTravelStatus = "Not Enough budget";
                                        travelStatusText.setText(finalTravelStatus);
                                        addFund();
                                    } else {
                                        finalTravelStatus = "Ready to Go";
                                        travelStatusText.setText(finalTravelStatus);
                                        addFund();
                                    }


                                }
                        }

                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }

        });

        travelFundText = findViewById(R.id.travelFund);
        travelFundText.setText(travelFund);

        addEvent = findViewById(R.id.addCalendar);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, getIntent().getStringExtra("eventTitle"));
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, getIntent().getStringExtra("eventLocation"));
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description );

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String date = dateFormat.format(calendar.getTime());
                String currentString = date;
                String[] separated = currentString.split("/");

                String endDate = getIntent().getStringExtra("eventDate");
                String[] separated2 = endDate.split("/");


                // Setting dates

                GregorianCalendar startDate =  new GregorianCalendar(Integer.valueOf(separated2[2]), Integer.valueOf(separated2[0])-1,  Integer.valueOf(separated2[1]));

                GregorianCalendar calDate = new GregorianCalendar(Integer.valueOf(separated2[2]), Integer.valueOf(separated2[0])-1,  Integer.valueOf(separated2[1])+1);

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startDate.getTimeInMillis());

                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                       calDate.getTimeInMillis());




                // Make it a full day event
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                // Making it private and shown as busy
                intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                startActivity(intent);
            }
        });
        confirmTravel = findViewById(R.id.cancelTravel);
        confirmTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmTravel.this);
                builder.setTitle("Hey");
                builder.setMessage("Are you sure to remove your travel to " +  getIntent().getStringExtra("eventTitle") + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeTravel();
                        Intent newIntent;
                        newIntent = new Intent(ConfirmTravel.this, MyTravelActivity.class);
                        startActivity(newIntent);
                        finish();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        checkMap = findViewById(R.id.checkMap);
        checkMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+getIntent().getStringExtra("eventLocation"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


    }

    private void removeTravel() {
        String travelid = getIntent().getStringExtra("travelId");

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_REMOVETRAVEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    ConfirmTravel.this).create();


                            alertDialog1.setTitle("Hey");
                            alertDialog1.setMessage(obj.getString("message"));
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alertDialog1.show();




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error Jason" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("travelid", travelid);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }

    public void addFund() {

       String travelid = getIntent().getStringExtra("travelId");
       String currentFunds = String.valueOf(yourFund);
       String travelStats =  finalTravelStatus;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDFUNDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    ConfirmTravel.this).create();


                            alertDialog1.setTitle("Hey");
                            alertDialog1.setMessage(obj.getString("message"));
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alertDialog1.show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error Jason" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("travelid", travelid);
                params.put("currentFund", currentFunds);
                params.put("travelStatus", travelStats);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }



}









