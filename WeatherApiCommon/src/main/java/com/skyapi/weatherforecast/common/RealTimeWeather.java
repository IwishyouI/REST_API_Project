package com.skyapi.weatherforecast.common;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "realtime_weather")
public class RealTimeWeather {

    @Column(name = "location_code")
    @Id
    private String locationCode;

    private int temperature;

    private int humidity;

    private int precipitation;

    private String status;

    private int windSpeed;

    private Date last_updated;


    @OneToOne
    @JoinColumn(name = "location_code")
    @MapsId
    private Location location;



    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.locationCode = location.getCode();
        this.location = location;
    }
}
