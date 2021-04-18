package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
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

    RecyclerView recyclerView;
    AdapterFriendsList friendsListAdapter;
    List<ModelFriends> modelFriendsList;

    RecyclerView recyclerViewFriendRequest;
    AdapterFriendRequest friendRequestAdapter;
    List<ModelFriendRequest> modelFriendRequestList;

    FloatingActionButton goToAddFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View friendsFragment = inflater.inflate(R.layout.fragment_friends, container, false);

            goToAddFriend = friendsFragment.findViewById(R.id.goToAddFriend);
            goToAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AddFriendActivity.class));
                }
            });
            modelFriendsList = new ArrayList<>();
            recyclerView = (RecyclerView) friendsFragment.findViewById(R.id.friendsRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(friendsFragment.getContext()));

            viewFriends();

            modelFriendRequestList = new ArrayList<>();
            recyclerViewFriendRequest = (RecyclerView) friendsFragment.findViewById(R.id.requestRecyclerView);
            recyclerViewFriendRequest.setHasFixedSize(true);
            recyclerViewFriendRequest.setLayoutManager(new LinearLayoutManager(friendsFragment.getContext()));


           // modelFriendRequestList.add(new ModelFriendRequest(1, 1,"https://media.cntraveler.com/photos/5668630dc3c9e01555a4d421/master/pass/palawan-philippines-coron-cr-alamy.jpg", "palawan"));


            viewFriendRequests();


        return friendsFragment;
    }

    private void viewFriendRequests() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETFRIENDREQUESTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int requestId = placesObject.getInt("requestId");
                                int requestor_userId = placesObject.getInt("requestor_userId");
                                String requestorName = placesObject.getString("requestorName");
                                String requestorImage = placesObject.getString("requestorImage");

                                modelFriendRequestList.add(new ModelFriendRequest(requestor_userId,  requestId, Constants.URL_PROFILE+requestorImage,  requestorName));

                            }

                            friendRequestAdapter = new AdapterFriendRequest(getContext(),  modelFriendRequestList);
                            recyclerViewFriendRequest.setAdapter(friendRequestAdapter);

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error JSON "+ e, Toast.LENGTH_LONG).show();
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

                String userid = String.valueOf(SharedPrefManager.getUid());

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    private void viewFriends() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETFRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++){
                                JSONObject placesObject = places.getJSONObject(i);

                                int friendId = placesObject.getInt("friend_id");
                                int friend_userId = placesObject.getInt("friend_userId");
                                String friendName = placesObject.getString("friendName");
                                String friendImage = placesObject.getString("friendImage");

                                modelFriendsList.add(new ModelFriends(friend_userId, Constants.URL_PROFILE+friendImage, friendName));

                            }

                            friendsListAdapter = new AdapterFriendsList(getContext(),  modelFriendsList);
                            recyclerView.setAdapter(friendsListAdapter);

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error JSON "+ e, Toast.LENGTH_LONG).show();
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

                String userid = String.valueOf(SharedPrefManager.getUid());

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}