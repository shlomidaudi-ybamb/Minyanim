package com.example.minyanim.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.minyanim.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class LocationActivity extends AppCompatActivity implements ActivityResultCallback<Map<String, Boolean>> {

    //                       --input type--
//    private ActivityResultLauncher<Void> getThumb;

    private FusedLocationProviderClient locationProvider;
    private OnSuccessListener<Location> successListener;

    protected Geocoder coder;


//    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationProvider = LocationServices.getFusedLocationProviderClient(this);

        coder = new Geocoder(this, Locale.getDefault());

//        getLocation();
    }

    public String toAddress(LatLng latLng) {
        try {
            List<Address> addresses = coder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            return addresses.get(0).getAddressLine(0); // TODO be more precise
        } catch (IOException e) { // no such address
            e.printStackTrace();
            return "";
        }
    }

    public OnSuccessListener<Location> getSuccessListener() {
        return successListener;
    }

    public void setSuccessListener(OnSuccessListener<Location> successListener) {
        this.successListener = successListener;
    }


        private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), this);

//     callback for permissions:

    @Override
    public void onActivityResult(Map<String, Boolean> result) {
        if (result != null) {
            boolean fine = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
            boolean coarse = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (fine & coarse) {
//            if (coarse) {
                getLocation();
            } else {
                Toast.makeText(LocationActivity.this, "Permission not granted...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

//    @SuppressLint("MissingPermission") // permissions are checked. it's a false warning
    protected void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
         checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationProvider.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(this, successListener);
        } else {
            // request permissions and try again
            requestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
            return;
        }

//        createLocationCallback(); // this must come first
//        createLocationRequest(); // because this one uses the callback
    }

//    private void createLocationCallback() {
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                String str = "";
//                for (Location loc : locationResult.getLocations()) {
//                    // update ui with location data
//                    double lat = loc.getLatitude();
//                    double lon = loc.getLongitude();
//
//                    Toast.makeText(LocationActivity.this, "lat,long: " + lat + " " + lon, Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//
//    }
}