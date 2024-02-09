package com.skyapi.weatherforecast.common;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location {

    @Column(length = 12, nullable = false, unique = true)
    @Id
    @NotNull(message = "code must not be null")
    @Length(min = 3,max = 12,message = "code must have 3-12 characters" )
    private String code;

    @Column(length = 128, nullable = false)
    @JsonProperty("city_name")
    @NotNull(message = "cityName must not be null")
    private String cityName;

    @Column(length = 128)
    @JsonProperty("region_name")
    private String regionName;

    @Column(length = 64, nullable = false)
    @JsonProperty("country_name")
    @NotNull(message = "countryName must not be null")
    private String countryName;

    @Column(length = 2, nullable = false)
    @JsonProperty("country_code")
    @NotNull(message = "countryCode must not be null")
    @Length(min = 1,max = 2,message = "countryCode must have 1-2 Characters")
    private String countryCode;

    private boolean enabled;

    @JsonIgnore
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
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        return Objects.equals(code, other.code);
    }


    @Override
    public String toString() {
        return "Location [code=" + code + ", cityName=" + cityName + ", regionName=" + regionName + ", countryName="
                + countryName + ", countryCode=" + countryCode + ", enabled=" + enabled + ", trashed=" + trashed + "]";
    }

}