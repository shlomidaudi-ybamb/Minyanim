package com.example.minyanim.model;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalTime;

public class Minyan {


    private String uid;
    private final Tfila tfila;
    private String name;
    private LocalTime time;
    private LatLng geoLocation;

    // one-time or permanent?


    // TODO Prayers list

    public enum Tfila { SHACHARIT, MINCHA, ARVIT }


    public Minyan(String uid) {
        this.uid = uid;
        tfila = Tfila.SHACHARIT;
        time = null;
        geoLocation = null;
        name = null;
    }

    public Minyan(String uid, String name, Tfila tfila, LocalTime time, LatLng geoLocation) {
        this.uid = uid;
        this.tfila = tfila;
        this.time = time;
        this.geoLocation = geoLocation;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public LatLng getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(LatLng geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Tfila getTfila() {
        return tfila;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
