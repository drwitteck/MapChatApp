package com.example.derekwitteck.mapchatapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.derekwitteck.mapchatapp.R;
import com.example.derekwitteck.mapchatapp.RequestQueueSingleton;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    private EditText mEditText;
    private String username;
    private String mJsonURL = "https://kamorris.com/lab/register_location.php";
    RequestQueue requestQueue;

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

        requestQueue = RequestQueueSingleton.getInstance(getActivity())
                .getRequestQueue();

        StringRequest postRequest = new StringRequest(Request.Method.POST, mJsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", response);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", username);
                params.put("Latitude", "123");
                params.put("Longitude", "456");

                return params;
            }
        };
        requestQueue.add(postRequest);
        // Inflate the layout for this fragment
        return v;
    }
}
