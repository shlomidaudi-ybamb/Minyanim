package com.example.minyanim.activities;

import androidx.annotation.NonNull;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.example.minyanim.model.MyGeoLocation;
import com.example.minyanim.model.MyTime;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;

public class CreateMinyanActivity extends LocationActivity implements OnMapReadyCallback, OnCompleteListener<Void>, OnFailureListener, OnSuccessListener<Void> {

    // 1. choose location - V
    // 2. choose time - V
    // 3. upload to firebase - V

    // TODO for some reason the connection to the database is always closed. this is a patch, giving the address explicitly:
    static final String DB_ADDRESS = "https://minyanim-1afba-default-rtdb.europe-west1.firebasedatabase.app";
    FirebaseAuth fbAuth;
    FirebaseDatabase fbdb;

    MyTime minyanTime;

    MapView mvMap;
    GoogleMap gmap;
    Marker marker;
    MyGeoLocation minyanLocation;

    TextView tvMinyanTime;
    EditText etMinyanName, etMinyanAddress;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_minyan);
        initViews();

        // init google map
        mvMap.onCreate(null);
        mvMap.getMapAsync(this);

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
                minyanTime,
                minyanLocation
        );

        // upload to firebase
        DatabaseReference ref = fbdb.getReference("minyanim").push();
        ref.setValue(m)
//                .addOnCompleteListener(this)
                .addOnFailureListener(this)
                .addOnSuccessListener(this);

    }

    @Override
    public void onSuccess(Void unused) {
        Snackbar.make(this, etMinyanAddress, "Minyan craeted!", BaseTransientBottomBar.LENGTH_SHORT).show();

    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        // inform user
        Snackbar.make(this, etMinyanAddress, "המניין נוצר!", BaseTransientBottomBar.LENGTH_SHORT).show();

        // close activity
        finish();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.d("Task failure", e.toString());
    }

    // 2. choose time

    public void chooseTime(View view) {
        TimePickerDialog tpd = new TimePickerDialog(this,
                (view1, hourOfDay, minute) -> {
                    minyanTime = new MyTime(hourOfDay, minute);
                    tvMinyanTime.setText(minyanTime.toString());
                }
                , 6, 0, true);
        tpd.show();
    }

    // 3. choose location

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;

        initMinyanLocation();

        gmap.setOnMapClickListener(latLng -> {
            // remove previous marker
            if (marker != null) // TODO does not remove the marker set by initMinyanLocation
                marker.remove();
            // place marker where user clicked
            String address = toAddress(latLng);
            marker = gmap.addMarker(new MarkerOptions().position(latLng).title(address));
            // change address on screen
            etMinyanAddress.setText(address);
            // update minyan location
            minyanLocation = new MyGeoLocation(latLng);
        });
    }

    private void initMinyanLocation() {
        setSuccessListener(location -> {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            minyanLocation = new MyGeoLocation(lat, lon);
            // set map to current location
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(minyanLocation.toLatLng(), 10f));
            // add marker to the current location
            String address = toAddress(minyanLocation.toLatLng());
            marker = gmap.addMarker(new MarkerOptions().position(minyanLocation.toLatLng()).title(address));
            // change address on screen
            etMinyanAddress.setText(address);

//            Toast.makeText(CreateMinyanActivity.this, String.format("Your location is (%f,%f)", lat, lon), Toast.LENGTH_LONG).show();
        });
        getLocation();

    }


    // ------ life cycle methods (for the mapView) ------ //

    @Override
    protected void onStart() {
        super.onStart();
        mvMap.onStart();

        // init firebase
        fbAuth = FirebaseAuth.getInstance();
        fbdb = FirebaseDatabase.getInstance(DB_ADDRESS);

        initMinyanLocation();
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