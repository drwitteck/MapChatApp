package com.example.derekwitteck.mapchatapp.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.derekwitteck.mapchatapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    GoogleMap mGoogleMap;
    LocationListener ll;
    LocationManager lm;
    Location currentLocation;
    LatLng latLng;
    View mView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = mView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

//        MarkerOptions marker = new MarkerOptions().position(latLng).title("My Location");
//        mGoogleMap.addMarker(marker);

//        ll = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLng, 16);
//                mGoogleMap.animateCamera(cu);
//
//                currentLocation = location;
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };

        return mView;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mMapView = mView.findViewById(R.id.map);
//        if(mMapView != null) {
//            mMapView.onCreate(null);
//            mMapView.onResume();
//            mMapView.getMapAsync(this);
//        }
//    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        MapsInitializer.initialize(getContext());
//
//        mGoogleMap = googleMap;
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        MarkerOptions marker = new MarkerOptions().position(latLng).title("My Location");
//        mGoogleMap.addMarker(marker);
//
//        ll = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLng, 16);
//                mGoogleMap.animateCamera(cu);
//
//                currentLocation = location;
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
//    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        mMapView.onResume();
//        if(getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED){
//            requestUpdates();
//        }else{
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
//        }
    }

    @Override
    public void onPause(){
        super.onPause();
        mMapView.onPause();
        //lm.removeUpdates(ll);
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

//    @SuppressWarnings("MissingPermission")
//    private void requestUpdates(){
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,ll);
//        lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0,ll);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            requestUpdates();
//        }else{
//            Toast.makeText(getActivity(), "Not granted.", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
    }
}
