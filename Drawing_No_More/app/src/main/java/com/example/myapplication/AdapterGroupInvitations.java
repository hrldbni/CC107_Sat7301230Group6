package com.example.myapplication;

import android.content.Context;
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

public class AdapterGroupInvitations extends RecyclerView.Adapter<AdapterGroupInvitations.AdapterGroupInvitationsViewHolder>{

    private Context mCtx;
    private List<ModelGroupInvitations> modelGroupInvitationsList;

    String groupTravelId = "";
    String groupTravelAdmin = "";
    String travelIdText = "";
    String groupTravelToInvite = "";

    public AdapterGroupInvitations(Context mCtx, List<ModelGroupInvitations> modelGroupInvitationsList) {
        this.mCtx = mCtx;
        this.modelGroupInvitationsList = modelGroupInvitationsList;
    }

    @NonNull
    @Override
    public AdapterGroupInvitationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_group_travel_invitations, null);
        return new AdapterGroupInvitations.AdapterGroupInvitationsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGroupInvitationsViewHolder holder, int position) {
        ModelGroupInvitations modelGroupInvitations = modelGroupInvitationsList.get(position);


        //Getting the photo and name of the place given the Travel I
        String requestId = String.valueOf(modelGroupInvitations.getRequestId()).trim();
        String travelId = String.valueOf(modelGroupInvitations.getTravelId()).trim();

                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            Constants.URL_GETTRAVELIMAGENAME,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        Glide.with(mCtx)
                                                .load(jsonObject.getString("Travel Image"))
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .placeholder(R.drawable.loader)
                                                .into(holder.placeImage);

                                         holder.placeTitle.setText(jsonObject.getString("Travel Destination"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(mCtx, "Error in Json" + e, Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();
                            params.put("travelId",travelId);
                            return params;
                        }
                    };

                    RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);




                    holder.acceptTravelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String travelId = String.valueOf(modelGroupInvitations.getTravelId()).trim();

                            StringRequest stringRequest = new StringRequest(
                                    Request.Method.POST,
                                    Constants.URL_ACCEPTTRAVELINVITATION,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);

                                                Toast.makeText(mCtx, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(mCtx, "Error in Json" + e, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                            ){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<>();
                                    params.put("request_id", requestId);
                                    params.put("group_travel_code", groupTravelId);
                                    params.put("group_travel_admin", groupTravelAdmin);
                                    params.put("group_travel_to_invite", groupTravelToInvite);
                                    return params;
                                }
                            };

                            RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest);



                        }
                    });

                    holder.declineTravelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mCtx, "CAncel travel Request for " + modelGroupInvitations.requestId, Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    @Override
    public int getItemCount() {
        return modelGroupInvitationsList.size();
    }

    class AdapterGroupInvitationsViewHolder extends RecyclerView.ViewHolder{


        ImageView placeImage;
        TextView placeTitle;
        Button acceptTravelBtn, declineTravelBtn;

        public AdapterGroupInvitationsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.placeImage);
            placeTitle = itemView.findViewById(R.id.placeTitle);
            acceptTravelBtn = itemView.findViewById(R.id.acceptTravel);
            declineTravelBtn = itemView.findViewById(R.id.declineInvitation);
        }
    }

    private void getTravelData(String requestIdText) {
        String requestId = requestIdText;

        StringRequest stringRequest1 = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETGROUPINVITATIONSDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            groupTravelId = jsonObject.getString("Group Travel Code");
                            groupTravelAdmin = jsonObject.getString("Group Travel Admin");
                            travelIdText = jsonObject.getString("Travel Id");
                            groupTravelToInvite = jsonObject.getString("'Group Travel To Invite");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mCtx, "Error in Json" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("requestId",requestId);
                return params;
            }
        };

        RequestHandler.getInstance(mCtx).addToRequestQueue(stringRequest1);

    }

}
