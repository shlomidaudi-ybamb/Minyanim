package com.example.minyanim.activities;

import android.content.Context;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.minyanim.model.Minyan;

public class MinyanAdapter extends ArrayAdapter<Minyan> {


    public MinyanAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
