package com.skyapi.weatherforecast.common;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weather_daily")
public class DailyWeather {


    @EmbeddedId
    public DailyWeatherId id = new DailyWeatherId();

    private int minTemp;
    private int maxTemp;
    private int precipitation;

    @Column(length = 50)
    private String status;

    public DailyWeatherId getId() {
        return id;
    }

    public void setId(DailyWeatherId id) {
        this.id = id;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
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

    public DailyWeather status(String status) {
        setStatus(status);
        return this;
    }

    public DailyWeather precipitation(int precipitation) {
        setPrecipitation(precipitation);
        return this;
    }

    public DailyWeather month(int month) {
        this.id.setMonth(month);
        return this;
    }
    public DailyWeather dayOfMonth(int dayofMonth) {
        this.id.setDayOfMonth(dayofMonth);
        return this;
    }

    public DailyWeather minTemp(int minTemp) {
        setMinTemp(minTemp);
        return this;
    }

    public DailyWeather maxTemp(int maxTemp) {
        setMaxTemp(maxTemp);
        return this;
    }

    public DailyWeather location(Location location) {
        id.setLocation(location);
        return this;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "id=" + id +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", precipitation=" + precipitation +
                ", status='" + status + '\'' +
                '}';
    }
}
