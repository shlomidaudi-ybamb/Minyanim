package com.example.minyanim.activities;

import androidx.annotation.NonNull;

import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class CreateMinyanActivity extends LocationActivity implements OnMapReadyCallback, TimePickerDialog.OnTimeSetListener, OnCompleteListener<Void> {

    // 1. choose location - V
    // 2. choose time
    // 3. upload to firebase - V

    FirebaseAuth fbAuth;
    FirebaseDatabase fbdb;

    LocalTime chosenTime;

    MapView mvMap;
    GoogleMap gmap;
    Marker marker;
    Geocoder coder;
    LatLng minyanLocation;

    TextView tvMinyanTime;
    EditText etMinyanName, etMinyanAddress;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_minyan);
        initViews();

        mvMap.onCreate(null);
        mvMap.getMapAsync(this);
        coder = new Geocoder(this, Locale.getDefault());

        fbAuth = FirebaseAuth.getInstance();
        fbdb = FirebaseDatabase.getInstance();

        initMinyanLocation();
    }



    private void initViews() {
        mvMap = findViewById(R.id.mvMap);
        tvMinyanTime = findViewById(R.id.tvMinyanTime);
        etMinyanName = findViewById(R.id.etMinyanName);
        etMinyanAddress = findViewById(R.id.etAddress);
//        String fname = this.getClass().getDeclaredFields()[0].getName();
//        R.id.class.getDeclaredField(fname);
        btnUpload = findViewById(R.id.btnCreateMinyan);
    }

    public void createMinyan(View view) {
        FirebaseUser user = fbAuth.getCurrentUser();
        String uid = user.getUid();
        Minyan m = new Minyan(
                uid, // user id
                etMinyanName.getText().toString(), // minyan name
                Minyan.Tfila.SHACHARIT, // default
                chosenTime,
                minyanLocation
        );

        // upload to firebase
        DatabaseReference ref = fbdb.getReference("minyanim").push();
        ref.setValue(m).addOnCompleteListener(this);


    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        // inform user
        Snackbar.make(this, null, "Minyan craeted!", BaseTransientBottomBar.LENGTH_SHORT).show();

        // close activity
//        finish();
    }

    // 2. choose time

    public void chooseTime(View view) {
        TimePickerDialog tpd = new TimePickerDialog(this, this, 6, 0 ,true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    // 3. choose location

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;

        initMinyanLocation();

        gmap.setOnMapClickListener(latLng -> {
            // remove previous marker
            if (marker != null)
                marker.remove();
            // place marker where user clicked
            String address = toAddress(latLng);
            marker = gmap.addMarker(new MarkerOptions().position(latLng).title(address));
            // change address on screen
            etMinyanAddress.setText(address);
            // update minyan location
            minyanLocation = latLng;
        });
    }

    private void initMinyanLocation() {
        setSuccessListener(location -> {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            minyanLocation = new LatLng(lat, lon);
            // set map to current location
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(minyanLocation, 10f));
            // add marker to the current location
            String address = toAddress(minyanLocation);
            marker = gmap.addMarker(new MarkerOptions().position(minyanLocation).title(address));
            // change address on screen
            etMinyanAddress.setText(address);

//            Toast.makeText(CreateMinyanActivity.this, String.format("Your location is (%f,%f)", lat, lon), Toast.LENGTH_LONG).show();
        });
        getLocation();

    }

    private String toAddress(LatLng latLng) {
        try {
            List<Address> addresses = coder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            return addresses.get(0).getAddressLine(0); // TODO be more precise
        } catch (IOException e) { // no such address
            e.printStackTrace();
            return "";
        }
    }


    // ------ life cycle methods (for the mapView) ------ //

    @Override
    protected void onStart() {
        super.onStart();
        mvMap.onStart();
    }

    @Override
    protected void onStop() {
        mvMap.onStop();
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvMap.onLowMemory();
    }


    @Override
    protected void onPause() {
        mvMap.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvMap.onResume();

    }

}