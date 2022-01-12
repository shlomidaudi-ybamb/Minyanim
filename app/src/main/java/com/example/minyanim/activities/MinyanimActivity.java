package com.example.minyanim.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MinyanimActivity extends LocationActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    FirebaseAuth fba;
    FirebaseDatabase fbdb;

    ArrayList<Minyan> minyanList;
    MinyanAdapter minyanAdapter;
    int radius;

    Button btnCreateMinyan;
    TextView tvUserName;
    EditText etKm;
    SeekBar sbKmRange;
    RecyclerView rvMinyanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyanim);

        fba = FirebaseAuth.getInstance();
        fbdb = FirebaseDatabase.getInstance(CreateMinyanActivity.DB_ADDRESS);

        minyanList = new ArrayList<>();
        minyanAdapter = new MinyanAdapter(minyanList, this, this);
        initViews();

        radius = sbKmRange.getProgress();
        updateMinyanList();

    }

    private void initViews() {
        btnCreateMinyan = findViewById(R.id.btnCreateMinyan);
        tvUserName = findViewById(R.id.tvUsername);
        etKm = findViewById(R.id.etKm);
        sbKmRange = findViewById(R.id.sbKmRange);
        // recycler view
        rvMinyanList = findViewById(R.id.rvMinyanList);
        rvMinyanList.setLayoutManager(new LinearLayoutManager(this));
        rvMinyanList.setAdapter(minyanAdapter);
        // seek bar
        sbKmRange.setOnSeekBarChangeListener(this);
        etKm.setText(""+sbKmRange.getProgress());
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
        radius = progress;
        etKm.setText(""+progress);
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
        int currentRadius = radius;
        // get the minyans-list from firebase
        fbdb.getReference("minyanim").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // check that the radius is the same one as when it was launched, using closure
                // (comes to prevent multiple colliding updates
                if (currentRadius == radius) {
                    minyanList.clear();
                    for (DataSnapshot mss : dataSnapshot.getChildren()) {
                        // TODO filter according to radius
                        minyanList.add(mss.getValue(Minyan.class));
                    }
                    // put in the recycler-view
                    minyanAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        // for now - do nothing
//        Minyan m = (Minyan) v.getTag();

        // TODO move to minyan activity
    }
}