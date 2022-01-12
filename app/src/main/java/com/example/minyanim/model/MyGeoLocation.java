package com.example.minyanim.model;

import com.google.android.gms.maps.model.LatLng;

public class MyGeoLocation {

    private double latitude, longitude;

    public MyGeoLocation(){} // empty ctor

    public MyGeoLocation(LatLng latLng) {
        this(latLng.latitude, latLng.longitude);
    }

    public MyGeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }
}
