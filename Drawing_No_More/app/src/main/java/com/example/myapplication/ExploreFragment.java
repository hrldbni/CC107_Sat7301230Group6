package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    CardView exploreMoreBtn;

    RecyclerView travelListsRecyclerView;
    AdapterUserTravels userTravelsAdapter;
    List<ModelUserTravels> modelUserTravelsList;
    private ProgressDialog progressDialog;
    TextView totalTravels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        exploreMoreBtn = view.findViewById(R.id.exploreMoreBtn);

        exploreMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FindPlace.class));
            }
        });

        modelUserTravelsList = new ArrayList<>();
        travelListsRecyclerView = (RecyclerView) view.findViewById(R.id.travelListsRecyclerView);
        travelListsRecyclerView.setHasFixedSize(true);
        travelListsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait for a moment...");

        totalTravels = (TextView) view.findViewById(R.id.totalTravels);

        getTravelLists();

        return view;

    }

    private void getTravelLists() {
            String userid = String.valueOf(SharedPrefManager.getUid());

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while retrieving your Travels");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_GETUSERTRAVELS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();
                                JSONArray places = new JSONArray(response);
                                totalTravels.setText("Travels("+String.valueOf(places.length())+")");

                                for (int i = 0; i < places.length(); i++){
                                    JSONObject userTravelsObject = places.getJSONObject(i);

                                    String travelid =  userTravelsObject.getString("travelId");
                                    String travelDate = userTravelsObject.getString("travelDate");
                                    //String travelFund = userTravelsObject.getString("travelFund");
                                    //String currentFund = userTravelsObject.getString("currentFund");
                                    String travelDestination = userTravelsObject.getString("travelDestination");
                                    String travelPlaceImage = userTravelsObject.getString("placeimage");
                                    String travelStatus = userTravelsObject.getString("travelStatus");
                                    //String travelLocation = userTravelsObject.getString("travelLocation");
                                    //String travelType = userTravelsObject.getString("travelType");

                                    modelUserTravelsList.add(new ModelUserTravels(Integer.valueOf(travelid),travelDate,travelDestination,travelStatus,travelPlaceImage));
                                }

                                userTravelsAdapter = new AdapterUserTravels(getContext(), modelUserTravelsList);
                                travelListsRecyclerView.setAdapter(userTravelsAdapter);



                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Error Jason" + e, Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userTravelId", userid);
                    return params;
                }
            };

            RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}