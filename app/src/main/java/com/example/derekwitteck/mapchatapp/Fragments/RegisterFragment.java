package com.example.derekwitteck.mapchatapp.Fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.derekwitteck.mapchatapp.R;
import com.example.derekwitteck.mapchatapp.RequestQueueSingleton;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    private EditText mEditText;
    private Button mSendButton;
    private String username, latitudeString, longitudeString;
    private String mJsonURL = "https://kamorris.com/lab/register_location.php";
    RequestQueue requestQueue;
    LocationManager lm;
    LocationListener ll;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mEditText = v.findViewById(R.id.editTextUsername);
        username = mEditText.getText().toString();

        mSendButton = v.findViewById(R.id.buttonSend);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText.getText().toString())){
                    mEditText.setError("Please enter a username");
                }



                lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                ll = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitudeString = String.valueOf(location.getLatitude());
                        longitudeString = String.valueOf(location.getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                requestQueue = RequestQueueSingleton.getInstance(getActivity())
                        .getRequestQueue();

//                Map<String, String> params = new HashMap<>();
//                params.put("Username", username);
//                params.put("Latitude", latitudeString);
//                params.put("Longitude", longitudeString);

                JsonObjectRequest postRequest = new JsonObjectRequest(mJsonURL, new JSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                VolleyLog.v("Response", response);
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.v("Error.Response", error.getMessage());
                            }
                        }
                ){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("Username", username);
                        params.put("Latitude", latitudeString);
                        params.put("Longitude", longitudeString);

                        return params;
                    }
                };

                requestQueue.add(postRequest);
            }
        });

        return v;
    }
}
