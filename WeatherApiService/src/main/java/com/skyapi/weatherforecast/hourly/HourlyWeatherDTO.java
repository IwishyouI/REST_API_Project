package com.skyapi.weatherforecast.hourly;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@JsonPropertyOrder({"hour_of_day", "temperature", "precipitation", "status"})
public class HourlyWeatherDTO {


    @JsonProperty("hour_of_day")
    @Range(min = 0, max = 31, message = "hourOfDay must be in the range of 0 to 31")
    private int hourOfDay;
    @Range(min = -50, max = 50, message = "temperature must be in the range of -50 to 50")
    private int temperature;

    @Range(min = 0, max = 99, message = "precipitation must be in the range of 0 to 99 percentage")
    private int precipitation;

    @Length(min = 3 , max = 50 , message = "Status must be in between 3-50")
    private String status;

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
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

    public HourlyWeatherDTO hourOfDay(int hour) {
        setHourOfDay(hour);
        return this;
    }

    public HourlyWeatherDTO status(String status) {
        setStatus(status);
        return this;
    }

    public HourlyWeatherDTO precipitation(int precipitation) {
        setPrecipitation(precipitation);
        return this;
    }

    public HourlyWeatherDTO temperature(int temperature) {
        setTemperature(temperature);
        return this;
    }

    @Override
    public String toString() {
        return "HourlyWeatherDTO{" +
                "hourOfDay=" + hourOfDay +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                ", status='" + status + '\'' +
                '}';
    }
}
