package com.example.minyanim.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MinyanimActivity extends LocationActivity {

    FirebaseAuth fba = FirebaseAuth.getInstance();

    ArrayList<Minyan> minyanList;

    Button btnCreateMinyan;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyanim);

        initViews();
    }

    private void initViews() {
        btnCreateMinyan = findViewById(R.id.btnCreateMinyan);
        tvUserName = findViewById(R.id.tvUsername);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fba = FirebaseAuth.getInstance();
        displayUsername();

        // TODO get the minyans-list from the firebase
    }

    private void displayUsername() {
        tvUserName.setText(fba.getCurrentUser().getDisplayName() + ", ");
    }


    public void openCreateMinyanActivity(View view) {
        Intent createMinyanActivity = new Intent(this, CreateMinyanActivity.class);
        startActivity(createMinyanActivity);
    }
}