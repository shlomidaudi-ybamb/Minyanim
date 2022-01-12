package com.example.minyanim.model;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalTime;

public class Minyan {


    private String uid;
    private Tfila tfila;
    private String name;
    private MyTime time;
    private MyGeoLocation geoLocation;

    // one-time or permanent?


    // TODO Prayers list

    public enum Tfila { SHACHARIT, MINCHA, ARVIT }

    public Minyan() {

    }


    public Minyan(String uid) {
        this.uid = uid;
        tfila = Tfila.SHACHARIT;
        time = null;
        geoLocation = null;
        name = null;
    }

    public Minyan(String uid, String name, Tfila tfila, MyTime time, MyGeoLocation geoLocation) {
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

    public MyGeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(MyGeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Tfila getTfila() {
        return tfila;
    }

    public MyTime getTime() {
        return time;
    }

    public void setTime(MyTime time) {
        this.time = time;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTfila(Tfila tfila) {
        this.tfila = tfila;
    }
}
