package com.skyapi.weatherforecast.common;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {


    @Column(length = 12, nullable = false, unique = true)
    @Id
    private String code;


    @Column(length = 128, nullable = false)
    private String cityName;

    @Column(length = 128)
    private String regionName;


    @Column(length = 64, nullable = false)
    private String countryName;

    @Column(length = 2, nullable = false)
    private String countryCode;

    private boolean enabled;

    private boolean trashed;

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

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    @Override
    public String toString() {
        return "Location{" +
                "code='" + code + '\'' +
                ", cityName='" + cityName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", enabled=" + enabled +
                ", trashed=" + trashed +
                '}';
    }
}
