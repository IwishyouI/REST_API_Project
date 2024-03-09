package com.skyapi.weatherforecast.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "realtime_weather")
public class RealTimeWeather {

    @Column(name = "location_code")
    @Id
    @JsonIgnore
    private String locationCode;


    @Range(min = -50, max = 50, message = "temperature must be in the range of -50 to 50 Celsius degree")
    private int temperature;

    @Range(min = 0, max = 99, message = "humidity must be in the range of 0 to 99 ")
    private int humidity;


    @Range(min = 0, max = 99, message = "precipitation must be in the range of 0 to 99 ")
    private int precipitation;

    @Length(min = 3 , max = 50 , message = "Status must be in between 3-50")
    private String status;

    @JsonProperty("wind_speed")
    private int windSpeed;

    @JsonProperty("last_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonIgnore
    private Date lastUpdated;


    @OneToOne
    @JoinColumn(name = "location_code")
    @MapsId
    @JsonIgnore
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


    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.locationCode = location.getCode();
        this.location = location;
    }

    @Override
    public String toString() {
        return "RealTimeWeather{" +
                "locationCode='" + locationCode + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", precipitation=" + precipitation +
                ", status='" + status + '\'' +
                ", windSpeed=" + windSpeed +
                ", lastUpdated=" + lastUpdated +
                ", location=" + location +
                '}';
    }
}
