package com.example.minyanim.activities;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minyanim.R;
import com.example.minyanim.model.Minyan;

import java.util.ArrayList;

public class MinyanAdapter extends RecyclerView.Adapter<MinyanAdapter.MinyanViewHolder> {

    private ArrayList<Minyan> minyans;
    private LocationActivity context;
    private View.OnClickListener listener;

    public MinyanAdapter(ArrayList<Minyan> minyans, LocationActivity context, View.OnClickListener viewOnClickListener) {
        this.minyans = minyans;
        this.context = context;
        this.listener = viewOnClickListener;
    }

    @NonNull
    @Override
    public MinyanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View minyanView = li.inflate(R.layout.recycleritem_minyan, parent, false);
        return new MinyanViewHolder(minyanView);
    }

    @Override
    public void onBindViewHolder(@NonNull MinyanViewHolder holder, int position) {
        holder.setMinyan(minyans.get(position));
    }

    @Override
    public int getItemCount() {
        return minyans.size();
    }

    public class MinyanViewHolder extends RecyclerView.ViewHolder {

        TextView tvMinyanName, tvMinyanAddress, tvMinyanTime;

        View myView;

        public MinyanViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
            // init views
            tvMinyanName = itemView.findViewById(R.id.tvMinyanName);
            tvMinyanAddress = itemView.findViewById(R.id.tvMinyanAddress);
            tvMinyanTime = itemView.findViewById(R.id.tvMinyanTime);

            // give them all the same listener
            tvMinyanName.setOnClickListener(listener);
            tvMinyanAddress.setOnClickListener(listener);
            tvMinyanTime.setOnClickListener(listener);
        }

        public void setMinyan(Minyan minyan) {
            tvMinyanName.setText(minyan.getName());
            tvMinyanTime.setText(minyan.getTime().toString());
            String address = context.toAddress(minyan.getGeoLocation());
            tvMinyanAddress.setText(address);

            myView.setTag(minyan);
        }

    }
}
