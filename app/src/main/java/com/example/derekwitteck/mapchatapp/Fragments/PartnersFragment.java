package com.example.derekwitteck.mapchatapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.derekwitteck.mapchatapp.User;
import com.example.derekwitteck.mapchatapp.R;
import com.example.derekwitteck.mapchatapp.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PartnersFragment extends Fragment {
    private TextView results;
    private String data = "";
    String userName;
    Double latitude, longitude;
    String stringLat, stringLong;
    private RequestQueue requestQueue;
    private static final String PARTNER_KEY = "partner_key";
    private User user;
    private PartnersInterface mPartnersInterface;

    public PartnersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get widget reference from XML layout
        View v = inflater.inflate(R.layout.fragment_partners, container, false);

//        user = (User) getArguments().getSerializable(PARTNER_KEY);

        FloatingActionButton addPartnerFab = v.findViewById(R.id.fabAddPartner);
        addPartnerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        Location location = mPartnersInterface.getUsersLocation();
        Toast.makeText(getContext(), location.toString(), Toast.LENGTH_LONG).show();

        results = v.findViewById(R.id.json_data);

        latitude = mPartnersInterface.getUsersLocation().getLatitude();
        longitude = mPartnersInterface.getUsersLocation().getLongitude();
        stringLat = Double.toString(latitude);
        stringLong = Double.toString(longitude);




        // Empty the TextView
        results.setText("");

        getPartners();

        return v;
    }

    public void getPartners(){
        // Initialize a new RequestQueueSingleton instance
        requestQueue = RequestQueueSingleton.getInstance(getActivity())
                .getRequestQueue();

        // Initialize a new JsonObjectRequest instance
        String jsonURL = "https://kamorris.com/lab/get_locations.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                jsonURL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            for (int i = 0; i < response.length(); i++){
                                // Get current json object
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                // Get the current jsonObject (json object) data
                                String username = jsonObject.getString("username");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                data += "Username: " + username + "\n\n";
                                data += "Latitude: " + latitude + "\n\n";
                                data += "Longitude: " + longitude + "\n\n";
                                data += "" + "\n\n";
                            }

                            results.setText(data);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Volley", "Error");
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public void addUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        EditText partnerInput = new EditText(getContext());
        alertBuilder.setView(partnerInput);
        userName = partnerInput.getText().toString();

        alertBuilder.setTitle("Add New Partner")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postRequest();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void postRequest(){
        String postURL = "https://kamorris.com/lab/register_location.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("POST Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Username", userName);
                params.put("latitude", stringLat);
                params.put("longitude", stringLong);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof PartnersInterface) {
            mPartnersInterface = (PartnersInterface) activity;
        } else {
            throw new RuntimeException("Not Implemented");
        }
    }

    public interface PartnersInterface {
        Location getUsersLocation();
    }
}
