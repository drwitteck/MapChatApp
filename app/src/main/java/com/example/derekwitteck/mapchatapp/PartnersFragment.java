package com.example.derekwitteck.mapchatapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.derekwitteck.mapchatapp.RequestQueueSingleton.getInstance;

public class PartnersFragment extends Fragment {
    private Context mContext;
    private Activity mActivity;
    private TextView results;
    private String mJsonURL = "https://kamorris.com/lab/get_locations.php";
    private String data = "";
    private RequestQueue requestQueue;
    static final String REQ_TAG = "MyTag";

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
        results = v.findViewById(R.id.json_data);

        // Empty the TextView
        results.setText("");

        // Initialize a new RequestQueueSingleton instance
        requestQueue = RequestQueueSingleton.getInstance(getActivity())
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
                        try{
//                            // Get the JSON array
//                            JSONObject userObj = response.getJSONObject(0);
//
//                            //Retrieve array from JSON object
//                            JSONArray userArray = userObj.getJSONArray("get_locations.php");

                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++){
                                // Get current json object
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                // Get the current jsonObject (json object) data
                                String username = jsonObject.getString("username");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                //Display the formatted json data in text view
//                                results.append(username + " " + latitude + " : " + longitude);
//                                results.append("\n\n");

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
}
