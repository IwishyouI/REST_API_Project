package com.skyapi.weatherforecast.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skyapi.weatherforecast.common.DailyWeather;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class LocationDTO {

    private String code;

    private String cityName;

    private String regionName;

    private String countryName;

    private String countryCode;
    private boolean enabled;

    public LocationDTO() {
    }

    public LocationDTO(String code, String cityName, String regionName, String countryName, String countryCode, boolean enabled) {
        this.code = code;
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.enabled = enabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocationDTO code(String code) {
        setCode(code);
        return this;
    }

    public LocationDTO cityName(String cityName) {
        setCityName(cityName);
        return this;
    }

    public LocationDTO regionName(String regionName) {
        setRegionName(regionName);
        return this;
    }

    public LocationDTO countryName(String coutryName) {
        setRegionName(coutryName);
        return this;
    }

    public LocationDTO enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

}
