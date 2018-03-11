package com.example.derekwitteck.mapchatapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.derekwitteck.mapchatapp.Fragments.MapFragment;
import com.example.derekwitteck.mapchatapp.Fragments.PartnersFragment;
import com.example.derekwitteck.mapchatapp.Fragments.RegisterFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Context context;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private final String TAG = "Main Activity";
    private EditText mEditText;
    private Button mSendButton;
    private String username, latitudeString, longitudeString;
    private String mJsonURL = "https://kamorris.com/lab/register_location.php";
    private RequestQueue requestQueue;
    private LocationManager lm;
    private LocationListener ll;

    private ViewPagerAdapter mViewPagerAdapter;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: Starting");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mEditText = findViewById(R.id.editTextUsername);
        username = mEditText.getText().toString();

        mSendButton = findViewById(R.id.buttonSend);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditText.getText().toString())){
                        mEditText.setError("Please enter a username");
                }

                lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

                requestQueue = RequestQueueSingleton.getInstance(context).getRequestQueue();

//                Map<String, String> params = new HashMap<>();
//                params.put("Username", username);
//                params.put("Latitude", latitudeString);
//                params.put("Longitude", longitudeString);

                JsonObjectRequest postRequest = new JsonObjectRequest(mJsonURL, new JSONObject(), new Response.Listener<JSONObject>() {
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
                )
                {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapFragment.requestUpdates();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            Toast.makeText(this, "Not granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisterFragment(), "Register");
        adapter.addFragment(new MapFragment(), "Map");
        adapter.addFragment(new PartnersFragment(), "Partners");
        viewPager.setAdapter(adapter);
    }
}
