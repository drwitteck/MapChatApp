package com.example.derekwitteck.mapchatapp;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derekwitteck on 3/11/18.
 */

public class GetPartners implements IGetPartners {

    private TextView results;
    private String mJsonURL = "https://kamorris.com/lab/get_locations.php";
    private String data = "";
    private RequestQueue requestQueue;
    private Partner partner;

    @Override
    public List<Partner> getPartners() {
        final List<Partner> allPartners = new ArrayList<>();

        requestQueue = RequestQueueSingleton.getInstance(this)
                .getRequestQueue();

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                mJsonURL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        Log.e("RESPONSE", response.toString());

                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                Partner partner = new Partner();
                                // Get current json object
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                // Get the current jsonObject (json object) data
                                String username = jsonObject.getString("username");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                partner.setUsername(username);
                                partner.setLatitude(latitude);
                                partner.setLatitude(longitude);

                                allPartners.add(partner);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        return allPartners;

    }

}
