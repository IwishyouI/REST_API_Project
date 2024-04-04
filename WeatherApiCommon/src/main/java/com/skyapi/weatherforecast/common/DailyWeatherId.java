package com.skyapi.weatherforecast.common;


import com.skyapi.weatherforecast.common.Location;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class DailyWeatherId implements Serializable {


    private int dayOfMonth;
    private int month;

    public DailyWeatherId() {
    }

    public DailyWeatherId(int dayOfMonth, int month, Location location) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name = "location_code")
    private Location location;

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "DailyWeatherId{" +
                "dayOfMonth=" + dayOfMonth +
                ", month=" + month +
                ", location=" + location +
                '}';
    }
}
