package com.example.minyanim.model;

import java.time.LocalTime;

public class MyTime {

    int hour, minute;

    public MyTime(){}

    public MyTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return LocalTime.of(hour, minute).toString();
    }
}
