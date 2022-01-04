package com.example.minyanim.model;

import android.location.Location;

import java.time.LocalTime;

public class Minyan {


    private User creator;
    private final Tfila tfila;
    private LocalTime time;
    private Location location;

    // TODO Prayers list

    public enum Tfila { SHACHARIT, MINCHA, ARVIT }


    public Minyan(User creator, Tfila tfila, LocalTime time, Location location) {
        this.creator = creator;
        this.tfila = tfila;
        this.time = time;
        this.location = location;
    }

    public User getCreator() {
        return creator;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
