package com.example.minyanim.model;

import java.time.LocalTime;

public class Minyan {

    private final Tfila tfila;
    private LocalTime time;

    public static enum Tfila { SHACHARIT, MINCHA, ARVIT }


    public Minyan (Tfila tfila, LocalTime time) {
        this.tfila = tfila;
        this.time = time;
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
