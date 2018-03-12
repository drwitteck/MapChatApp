package com.example.derekwitteck.mapchatapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class RegisterActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;
    private String username;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextView = findViewById(R.id.textViewInstructions);
        mEditText = findViewById(R.id.editTextUsername);
        Button mButton = findViewById(R.id.buttonSend);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                        }, 10);
                        return;
                    }

                    mFusedLocationClient.getLastLocation().addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null)
                                mLocation = location;
                        }
                    });

                username = mEditText.getText().toString();
                String latitude = String.valueOf(mLocation.getLatitude());
                mTextView.setText(latitude);
            }
        });
    }
}