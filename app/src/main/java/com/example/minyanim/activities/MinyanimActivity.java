package com.example.minyanim.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MinyanimActivity extends AppCompatActivity {

    ArrayList<Minyan> minyanList;

    MapView mvMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyanim);

        // get the minyans-list from the firebase


    }




    // ------ life cycle methods ------ //


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
//        createLocationRequest();
//        getLocation();
        mvMap.onResume();

    }
}