package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlaceViewerActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    ImageButton imgtravel;
    ImageButton imgprofile;
    EditText userid ,travelDate, travelFund, travelDestination;
    Button insertBtn;
    String place;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);
        travelDate = findViewById(R.id.travelDate);
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

        userid = (EditText) findViewById(R.id.userid);
        userid.setText(String.valueOf(SharedPrefManager.getInstance(this).getUid()));
        travelDestination = (EditText) findViewById(R.id.travelDestination);
        travelDestination.setText(place);

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
                addTravel();
            }
        });
    }

    private void addTravel() {

        final String userTravelId = userid.getText().toString().trim();
        final String travelDateText = travelDate.getText().toString().trim();
        final String travelFundText = travelFund.getText().toString().trim();
        final String travelDestinationText = travelDestination.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADDTRAVEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() + "Erro", Toast.LENGTH_LONG).show();
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

            TextView imageTitle = (TextView) findViewById(R.id.imageTitle);
            TextView imageLocation = (TextView) findViewById(R.id.imageLocation);
            ImageView imageViewer = (ImageView) findViewById(R.id.imageViewer);

            Glide.with(this)
                    .asBitmap()
                    .load(image_url)
                    .into(imageViewer);

            imageTitle.setText(image_title);
            imageLocation.setText(description);

            place = image_title;


        }
    }
    private void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        travelDate.setText(sdf.format(myCalendar.getTime()));


    }


}