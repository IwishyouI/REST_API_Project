package com.skyapi.weatherforecast.common;


import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class HourlyWeatherId implements Serializable {

    private int hourOfDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_code")
    private Location location;

    public HourlyWeatherId() {
    }

    public HourlyWeatherId(int hourOfDay, Location location) {
        this.hourOfDay = hourOfDay;
        this.location = location;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }




}
