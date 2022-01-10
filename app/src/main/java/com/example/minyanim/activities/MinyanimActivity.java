package com.example.minyanim.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MinyanimActivity extends AppCompatActivity {

    ArrayList<Minyan> minyanList;

    Button btnCreateMinyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyanim);

        initViews();

        // get the minyans-list from the firebase


    }

    private void initViews() {
        btnCreateMinyan = findViewById(R.id.btnCreateMinyan);
    }


    public void createMinyan(View view) {
        Intent createMinyanActivity = new Intent(this, CreateMinyanActivity.class);
        startActivity(createMinyanActivity);
    }
}