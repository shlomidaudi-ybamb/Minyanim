package com.example.minyanim.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MinyanimActivity extends LocationActivity implements SeekBar.OnSeekBarChangeListener {

    FirebaseAuth fba = FirebaseAuth.getInstance();

    ArrayList<Minyan> minyanList;

    Button btnCreateMinyan;
    TextView tvUserName;
    EditText etKm;
    SeekBar sbKmRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyanim);

        initViews();


    }

    private void initViews() {
        btnCreateMinyan = findViewById(R.id.btnCreateMinyan);
        tvUserName = findViewById(R.id.tvUsername);
        etKm = findViewById(R.id.etKm);
        sbKmRange = findViewById(R.id.sbKmRange);

        sbKmRange.setOnSeekBarChangeListener(this);
        etKm.setText(sbKmRange.getProgress());
    }

    @Override
    protected void onStart() {
        super.onStart();
        fba = FirebaseAuth.getInstance();
        displayUsername();

        updateMinyanList();
    }

    private void displayUsername() {
        tvUserName.setText(fba.getCurrentUser().getDisplayName() + ", ");
    }


    public void openCreateMinyanActivity(View view) {
        Intent createMinyanActivity = new Intent(this, CreateMinyanActivity.class);
        startActivity(createMinyanActivity);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        etKm.setText(progress);
        updateMinyanList();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // nothing
    }

    private void updateMinyanList() {
        // TODO get the minyans-list from the firebase

        // put in the recycler-view
    }
}