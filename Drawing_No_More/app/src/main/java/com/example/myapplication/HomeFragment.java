package com.example.myapplication;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ViewPager viewPager;
    Adapter adapter;
    Integer[] colors = null;
    List<Model> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    RecyclerView recyclerView;
    AdapterWhatsNew whatsNewAdapter;
    List<ModelWhatsNew> modelWhatsNewList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);

        models = new ArrayList<>();

       loadHotTravels();


        viewPager = home.findViewById(R.id.hotTravelsPage);

        viewPager.setPadding(16, 0,16, 0);



        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        modelWhatsNewList = new ArrayList<>();
        recyclerView = (RecyclerView) home.findViewById(R.id.whatsNewRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(home.getContext()));

        loadWhatsNew();

        return home;
    }

    private void loadWhatsNew() {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_PLACES,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
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


                                    modelWhatsNewList.add(new ModelWhatsNew(placeimage, placetitle, location));

                                }

                                whatsNewAdapter = new AdapterWhatsNew(getContext(), modelWhatsNewList);
                                recyclerView.setAdapter(whatsNewAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

            Volley.newRequestQueue(getContext()).add(stringRequest);


    }


    private void loadHotTravels() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETHOTTRAVELS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int hot_travel_id = placesObject.getInt("hot_travel_id");
                                String placetitle = placesObject.getString("place_title");
                                String location = placesObject.getString("place_location");
                                String placeimage = placesObject.getString("place_img");

                                models.add(new Model(placeimage, placetitle, location));


                            }
                            adapter = new Adapter(models, getContext());
                            viewPager.setAdapter(adapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}