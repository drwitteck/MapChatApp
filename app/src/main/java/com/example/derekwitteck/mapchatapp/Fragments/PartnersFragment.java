package com.example.derekwitteck.mapchatapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.derekwitteck.mapchatapp.R;
import com.example.derekwitteck.mapchatapp.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PartnersFragment extends Fragment {
    private TextView results;
    private String jsonURL = "https://kamorris.com/lab/get_locations.php";
    private String data = "";
    private RequestQueue requestQueue;
    private FloatingActionButton addPartnerFab;

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

        addPartnerFab = v.findViewById(R.id.fabAddPartner);
        addPartnerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        results = v.findViewById(R.id.json_data);

        // Empty the TextView
        results.setText("");

        // Initialize a new RequestQueueSingleton instance
        requestQueue = RequestQueueSingleton.getInstance(getActivity())
                .getRequestQueue();

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                jsonURL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        Log.e("RESPONSE", response.toString());

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

        // Inflate the layout for this fragment
        return v;
    }

    public void addUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        EditText partnerInput = new EditText(getContext());
        alertBuilder.setView(partnerInput);
        String userName = partnerInput.getText().toString();
        alertBuilder.setTitle("Add New Partner")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
}
