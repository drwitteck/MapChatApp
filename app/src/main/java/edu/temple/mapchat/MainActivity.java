package edu.temple.mapchat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.temple.mapchat.Fragments.GMapFragment;
import edu.temple.mapchat.Fragments.PartnersFragment;

public class MainActivity extends AppCompatActivity implements PartnersFragment.PartnersInterface {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LocationManager locationManager;
    private Location mLocation;
    User user;

    static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getLocation();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
/*(
            if (location != null) {
                double latitude = location.getLatitude();
                user.setLatitude(latitude);

                double longitude = location.getLongitude();
                user.setLongitude(longitude);

                Toast.makeText(this, "Coordinates" + latitude + " " + longitude, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Could not retrieve location.", Toast.LENGTH_SHORT).show();
            }
        }
        */
        }
        return mLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PartnersFragment(), "Partners");
        adapter.addFragment(new GMapFragment(), "Map");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //mapView.onSaveInstanceState(outState);
    }

    @Override
    public Location getUsersLocation() {
        return mLocation;
    }
}
