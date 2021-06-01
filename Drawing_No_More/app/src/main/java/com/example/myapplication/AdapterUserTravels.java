package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterUserTravels extends RecyclerView.Adapter<AdapterUserTravels.AdapterUserTravelsViewHolder>{

    Context mCtx;
    List<ModelUserTravels> modelUserTravelsList;

    public AdapterUserTravels(Context mCtx, List<ModelUserTravels> modelUserTravelsList) {
        this.mCtx = mCtx;
        this.modelUserTravelsList = modelUserTravelsList;
    }

    @NonNull
    @Override
    public AdapterUserTravelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.user_travel_lists, null);
        return new AdapterUserTravels.AdapterUserTravelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserTravelsViewHolder holder, int position) {
        ModelUserTravels modelUserTravels = modelUserTravelsList.get(position);

        Glide.with(mCtx)
                .load(modelUserTravels.getTravel_img())
                .placeholder(R.drawable.loader)
                .into(holder.userTravelImage);
        holder.userTravelSchedule.setText(modelUserTravels.getTravel_date());
        holder.userTravelStatus.setText(modelUserTravels.getTravelStatus());
        holder. userTravelTitle.setText(modelUserTravels.getTravel_destination());
        holder.previewTravelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String travelId = String.valueOf(modelUserTravels.getTravel_id());
                Intent newIntent;
                newIntent = new Intent(v.getRootView().getContext(), TravelDetailsActivity.class);
                newIntent.putExtra("travelId", travelId);
                mCtx.startActivity(newIntent);

            }
        });
        holder.cancelTravelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Hey");
                builder.setMessage("Are you sure to remove your travel to " +  modelUserTravels.getTravel_destination()+ "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeTravel();
                        Intent newIntent;
                        newIntent = new Intent(v.getRootView().getContext(), HomeActivity.class);
                        mCtx.startActivity(newIntent);
                        ((Activity) mCtx).finish();

                    }

                    private void removeTravel() {
                        String travelid = String.valueOf(modelUserTravels.getTravel_id()).toString().trim();

                        StringRequest stringRequest = new StringRequest(
                                Request.Method.POST,
                                Constants.URL_REMOVETRAVEL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject obj = new JSONObject(response);

                                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                                    v.getContext()).create();


                                            alertDialog1.setTitle("Hey");
                                            alertDialog1.setMessage(obj.getString("message"));
                                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });

                                            alertDialog1.show();




                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(v.getContext(), "Error Jason" + e, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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

                        RequestHandler.getInstance(v.getContext()).addToRequestQueue(stringRequest);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getRootView().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                }
        });



    }



    @Override
    public int getItemCount() {
        return modelUserTravelsList.size();
    }

    class AdapterUserTravelsViewHolder extends RecyclerView.ViewHolder {

        public  TextView userTravelTitle;
        public ImageView userTravelImage;
        public TextView userTravelSchedule;
        public TextView userTravelStatus;
        public Button cancelTravelBtn, previewTravelBtn;

        public AdapterUserTravelsViewHolder(@NonNull View itemView) {
            super(itemView);

            userTravelTitle = itemView.findViewById(R.id.userTravelLocation);
            userTravelImage = itemView.findViewById(R.id.userTravelImage);
            userTravelSchedule = itemView.findViewById(R.id.scheduleDateText);
            userTravelStatus =itemView.findViewById(R.id.statusText);
            cancelTravelBtn = itemView.findViewById(R.id.cancelTravel);
            previewTravelBtn = itemView.findViewById(R.id.previewTravel);

        }
    }
}
